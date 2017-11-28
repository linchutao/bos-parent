import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class HSSFTest {
	//@Test
	public void test1() throws FileNotFoundException, IOException {
		File file = new File("F:\\j2ee\\11、物流BOS系统\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			System.out.println();
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue()+" ");
			}
		}
	}
}
