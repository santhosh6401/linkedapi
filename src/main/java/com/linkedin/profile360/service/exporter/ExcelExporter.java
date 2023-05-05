package com.linkedin.profile360.service.exporter;

import com.linkedin.profile360.model.entity.ProfileEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ProfileEntity> listUsers;
    private Row row;
    private int count;

    public ExcelExporter(List<ProfileEntity> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Export");

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);
        row = sheet.createRow(0);
        //Center Align
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Cell cell = row.createCell(0);
        cell.setCellValue("Profiles");
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

        row = sheet.createRow(2);
        createCell(row, 0, "S.No", style);
        createCell(row, 1, "firstname", style);
        createCell(row, 2, "lastname", style);
        createCell(row, 3, "department", style);
        createCell(row, 4, "batch", style);
        createCell(row, 5, "mobileNo", style);
        createCell(row, 6, "emailId", style);
        createCell(row, 7, "linkedInUrl", style);
        createCell(row, 8, "linkedInProfileUrl", style);
        createCell(row, 9, "currentOccupation", style);
        createCell(row, 10, "Present Organization", style);

    }


    private void writeDataLines() {
        int rowCount = 3;
        count = 11;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (ProfileEntity exportEntity : listUsers) {
            count++;
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, rowCount - 3, style);
            createCell(row, columnCount++, exportEntity.getFirstname(), style);
            createCell(row, columnCount++, exportEntity.getLastname(), style);
            createCell(row, columnCount++, exportEntity.getDepartment(), style);
            createCell(row, columnCount++, exportEntity.getBatch(), style);
            createCell(row, columnCount++, exportEntity.getMobileNo(), style);
            createCell(row, columnCount++, exportEntity.getEmailId(), style);
            createCell(row, columnCount++, exportEntity.getLinkedInUrl(), style);
            createCell(row, columnCount++, exportEntity.getLinkedInProfileUrl(), style);
            createCell(row, columnCount++, exportEntity.getCurrentOccupation(), style);

            String presentOrganization = "";
            if (ObjectUtils.isEmpty(exportEntity.getCompanyExperienceDetails()) && ObjectUtils.isEmpty(exportEntity.getCompanyExperienceDetails().get(exportEntity.getCompanyExperienceDetails().size() - 1))) {
                presentOrganization = " ";
            } else {
                if (ObjectUtils.isEmpty(exportEntity.getCompanyExperienceDetails().get(exportEntity.getCompanyExperienceDetails().size() - 1).getCompany())) {
                    presentOrganization = " ";
                } else {
                    presentOrganization = exportEntity.getCompanyExperienceDetails().get(exportEntity.getCompanyExperienceDetails().size() - 1).getCompany();
                }
            }
            createCell(row, columnCount++, presentOrganization, style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
