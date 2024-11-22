package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import commonFunctions.FunctionLibrary;
import utilies.ExcelFileUtil;





public class DriverScript
{
	WebDriver driver;
	String inputpath ="./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	String TCSheet = "MasterTestCases";
	public void startTest()throws Throwable
	{
		String Module_Status="";
		String Module_New ="";
		//Create object for excel file util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//count no of rows in TCSheet
		int rc =xl.rowCount(TCSheet);
		Reporter.log("No of rows are::"+rc,true);
		//iterate all rows in TCsheet
		for(int i=1;i<=rc;i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
			//read corresponding sheet int one variable
				String TCModule = xl.getCellData(TCSheet, i, 1);
				//iterate all rows in TCModule
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read all cells from TCModule sheet
					String Object_Type =xl.getCellData(TCModule, j, 1);
					String Locator_Type =xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						//call methods from function library
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser(); 
							
						}
						if(Object_Type.equalsIgnoreCase("OpenUrl"))
						{
							FunctionLibrary.openurl();
							
						}
						if(Object_Type.equalsIgnoreCase("waitforElement"))
						{
							FunctionLibrary.WaitForElement(Locator_Type, Locator_Value, Test_Data);
							
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.ValidateTitle(Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closebrowser();
						}
						if(Object_Type.equalsIgnoreCase("dropDownElement"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("capturestock"))
						{
							FunctionLibrary.capturestock(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
						}	
						if(Object_Type.equalsIgnoreCase("capturesup"))
						{
							FunctionLibrary.capturesup(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
						}
						if(Object_Type.equalsIgnoreCase("capturecus"))
						{
							FunctionLibrary.capturecus(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase(" customerTable"))
						{
							FunctionLibrary.customerTable();
						}
						
						
				
						//Write as pass into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "PASS", outputpath);
						Module_Status="True";
					} catch (Exception e) {
						System.out.println(e.getMessage());
						
						//Write as Fail into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						Module_New="False";
						
					}
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//write as pass into status cell in TCSheet
					xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
				}
			}
			else
			{
				//write as blocked for test cases with are flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
				
			}
			
			
		}
		
		
	}
}	
  
