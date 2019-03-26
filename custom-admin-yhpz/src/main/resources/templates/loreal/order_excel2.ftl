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
  <Version>16.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>7995</WindowHeight>
  <WindowWidth>21570</WindowWidth>
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
  <Style ss:ID="s63">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="18"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s64">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s65">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="微软雅黑" x:CharSet="134" x:Family="Swiss" ss:Size="11"
    ss:Color="#000000" ss:Bold="1"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="18" ss:ExpandedRowCount="${orders?size+10}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="132"/>
   <Column ss:AutoFitWidth="0" ss:Width="140.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="71.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="116.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="81.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="162"/>
   <Column ss:Index="9" ss:AutoFitWidth="0" ss:Width="43.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="60.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="67.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="87.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="42.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="132.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="66.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="92.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="72"/>
   <Column ss:AutoFitWidth="0" ss:Width="109.5"/>
   <Row ss:AutoFitHeight="0">
    <Cell ss:MergeAcross="17" ss:MergeDown="1" ss:StyleID="s63"><Data
      ss:Type="String">订单表</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0"/>
   <Row ss:AutoFitHeight="0" ss:Height="15" ss:StyleID="s64">
    <Cell ss:StyleID="s65"><Data ss:Type="String">商户订单号</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">交易流水号</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">订单状态</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">下单时间</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品类型</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品编号</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品名称</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">商品属性</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">数量</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">实付款</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">省/直辖市</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">城市</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">区/县</Data></Cell>
    <Cell><Data ss:Type="String">学校</Data></Cell>
    <Cell><Data ss:Type="String">校区</Data></Cell>
    <Cell><Data ss:Type="String">证件号</Data></Cell>
    <Cell><Data ss:Type="String">姓名</Data></Cell>
    <Cell><Data ss:Type="String">电话</Data></Cell>
   </Row>
<#list orders as o>
   <Row ss:AutoFitHeight="0">
    <Cell><Data ss:Type="String">${o.orderNo!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.transactionId!}</Data></Cell>
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
    <Cell><Data ss:Type="String">${(o.createTime?string("yyyy-MM-dd HH:mm:ss"))!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.goodsType!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.goodsNo!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.goodsName!}</Data></Cell>
    <Cell><Data ss:Type="String">
        <#switch o.goodsProp!>
            <#case 0>普通<#break>
            <#case 1>换购<#break>
        </#switch>
    </Data></Cell>
    <Cell><Data ss:Type="String">${o.goodsCount!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.realPay!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.province!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.city!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.dist!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userSchool!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userSchoolDist!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userNo!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userFullName!}</Data></Cell>
    <Cell><Data ss:Type="String">${o.userTel!}</Data></Cell>
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
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
