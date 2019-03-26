<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-13T11:21:51Z</Created>
  <LastSaved>2006-09-13T11:21:55Z</LastSaved>
  <Version>12.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>11640</WindowHeight>
  <WindowWidth>19200</WindowWidth>
  <WindowTopX>0</WindowTopX>
  <WindowTopY>90</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Center"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s77">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="18"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s81">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s88">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="11"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="7" ss:ExpandedRowCount="10" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="100.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="112.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="73.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="123.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="62.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="65.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="100.5"/>
   <Row>
    <Cell ss:MergeAcross="6" ss:MergeDown="1" ss:StyleID="s77"><Data
      ss:Type="String">订单表</Data></Cell>
   </Row>
   <Row ss:Index="3" ss:Height="15" ss:StyleID="s81">
    <Cell ss:StyleID="s88"><Data ss:Type="String">订单号</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">下单时间</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">下单人</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">下单人所在学校</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">实付款</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">订单状态</Data></Cell>
    <Cell ss:StyleID="s88"><Data ss:Type="String">提货点</Data></Cell>
   </Row>

<#--<#list orders as o>-->
   <Row>
    <Cell><Data ss:Type="String">${o.orderNo!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.createTime!?string("yyyy-MM-dd HH:mm:ss")}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userFullName!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userSchool!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.discountFee!}</Data></Cell>
    <Cell><Data ss:Type="String">
        <#--订单状态 1未付款 2未提货 3已完成 4已撤销 5待退款 6已退款',-->
     <#switch o.orderStatus!>
      <#case 1>未付款<#break>
      <#case 2>未提货<#break>
      <#case 3>已完成<#break>
      <#case 4>已撤销<#break>
      <#case 5>待退款<#break>
      <#case 6>已退款<#break>
     </#switch>
    </Data></Cell>
    <Cell><Data ss:Type="String">${o.eventPickUpAddress!}</Data></Cell>
   </Row>
<#--</#list>-->

  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>200</HorizontalResolution>
    <VerticalResolution>200</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>4</ActiveRow>
     <ActiveCol>2</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
