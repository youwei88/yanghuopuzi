<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-13T11:21:51Z</Created>
  <LastSaved>2017-04-27T07:23:30Z</LastSaved>
  <Version>16.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>12240</WindowHeight>
  <WindowWidth>28800</WindowWidth>
  <WindowTopX>0</WindowTopX>
  <WindowTopY>0</WindowTopY>
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
  <Style ss:ID="s62">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
  </Style>
  <Style ss:ID="s64">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="16"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s65">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="11"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="${orderStatistic?size+10}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="123.75"/>
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="267"/>
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="112.5"/>
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="133.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="74.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="177"/>
   <Column ss:AutoFitWidth="0" ss:Width="117.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Row ss:AutoFitHeight="0">
    <Cell ss:MergeAcross="7" ss:MergeDown="1" ss:StyleID="s64"><Data
      ss:Type="String">订单商品统计</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0"/>
   <Row ss:AutoFitHeight="0" ss:Height="15">
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品分类</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品名称</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">销售数量</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">订单金额</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">订单状态</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">学校名称</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">开始时间</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">结束时间</Data></Cell>
   </Row>

<#list orderStatistic as od>
   <Row ss:AutoFitHeight="0">
    <Cell><Data ss:Type="String">${od.goodsType!}</Data></Cell>
    <Cell><Data ss:Type="String">${od.goodsName!}</Data></Cell>
    <Cell><Data ss:Type="String">${od.goodsCount!}</Data></Cell>
    <Cell><Data ss:Type="String">${od.realPay!}</Data></Cell>
       <Cell><Data ss:Type="String">
       <#--订单状态 1未付款 2未提货 3已完成 4已撤销 5待退款 6已退款',-->
           <#switch od.orderStatus!>
               <#case 1>未付款<#break>
               <#case 2>未提货<#break>
               <#case 3>已完成<#break>
               <#case 4>已撤销<#break>
               <#case 5>待退款<#break>
               <#case 6>已退款<#break>
           </#switch>
       </Data></Cell>
       <Cell><Data ss:Type="String">${od.userSchool!}</Data></Cell>
   <Cell><Data ss:Type="String">${(od.startTime?string("yyyy-MM-dd"))!}</Data></Cell>
   <Cell><Data ss:Type="String">${(od.endTime?string("yyyy-MM-dd"))!}</Data></Cell>
   </Row>
</#list>


  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Unsynced/>
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
     <ActiveRow>3</ActiveRow>
     <ActiveCol>7</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
