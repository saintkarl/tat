package com.retirement.tat.web.util;

import com.retirement.tat.core.dto.OutletMerReportDTO;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 6/2/16
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelBuilderUtil extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse httpServletResponse) throws Exception {
        List<OutletMerReportDTO> outletMerReportDTOs = (List<OutletMerReportDTO>) model.get("data");
        String filename = "complain" + System.currentTimeMillis();
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition",
                "attachment;filename="+filename+".xls");

        HSSFSheet sheet = hssfWorkbook.createSheet("Complaint");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = hssfWorkbook.createCellStyle();
        Font font = hssfWorkbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        //Date style
        CellStyle dateStyle = hssfWorkbook.createCellStyle();
        short df = hssfWorkbook.createDataFormat().getFormat("dd-mmm");

        // create header row
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Complaint");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Outlet");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Audit Date");
        header.getCell(2).setCellStyle(style);

        int rowCount = 1;

        for(OutletMerReportDTO outletMerReportDTO : outletMerReportDTOs) {
            HSSFRow row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(outletMerReportDTO.getValue());
            row.createCell(1).setCellValue(outletMerReportDTO.getOutletName());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            row.createCell(2).setCellValue(simpleDateFormat.format(outletMerReportDTO.getAuditDate().getTime()));
        }

    }
}
