package com.sapbasemodule.model;

import java.util.List;

public class ExcellSheetWrapper extends BaseWrapper {
	
	private ExcelSheetTable1 excelSheetTable1;
	
	private List<ExcelSheet> excelSheetTable2;

	
	public ExcellSheetWrapper() {
	}
	
	public ExcellSheetWrapper(ExcelSheetTable1 excelSheetTable1, List<ExcelSheet> excelSheetTable2) {
		this.excelSheetTable1 = excelSheetTable1;
		this.excelSheetTable2 = excelSheetTable2;
	}

	public ExcelSheetTable1 getExcelSheetTable1() {
		return excelSheetTable1;
	}

	public void setExcelSheetTable1(ExcelSheetTable1 excelSheetTable1) {
		this.excelSheetTable1 = excelSheetTable1;
	}

	public List<ExcelSheet> getExcelSheetTable2() {
		return excelSheetTable2;
	}

	public void setExcelSheetTable2(List<ExcelSheet> excelSheetTable2) {
		this.excelSheetTable2 = excelSheetTable2;
	}

	@Override
	public String toString() {
		return "ExcellSheetWrapper [excelSheetTable1=" + excelSheetTable1 + ", excelSheetTable2=" + excelSheetTable2
				+ "]";
	}
	
	
	

}
