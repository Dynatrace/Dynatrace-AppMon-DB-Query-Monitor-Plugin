<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>DB Query Monitor Plugin</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta content="Scroll Wiki Publisher" name="generator"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/liquid.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/print.css" media="print"/>
    <link type="text/css" rel="stylesheet" href="css/content-style.css" media="screen, projection, print"/>
    <link type="text/css" rel="stylesheet" href="css/screen.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/print.css" media="print"/>
</head>
<body>
                <h1>DB Query Monitor Plugin</h1>
    <div class="section-2"  id="121341710_DBQueryMonitorPlugin-Overview"  >
        <h2>Overview</h2>
    <p>
    </p>
            <img src="images_community/download/attachments/121341710/icon.png" alt="images_community/download/attachments/121341710/icon.png" class="confluence-embedded-image" />
            <p>
The monitor returns the row count from the specified query and up to 10 column values of the &quot;top&quot; row if desired (must be numeric value). There is also an additional measure detailing whether the connection to the database was successful or not    </p>
    <p>
    </p>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/121341710/DB_Query_Monitor_Properties.jpg" alt="images_community/download/attachments/121341710/DB_Query_Monitor_Properties.jpg" class="" />
            </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/121341710/DB_Query_Monitor_Measures.jpg" alt="images_community/download/attachments/121341710/DB_Query_Monitor_Measures.jpg" class="" />
            </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
            <img src="images_community/download/attachments/121341710/Dashboard_Example.jpg" alt="images_community/download/attachments/121341710/Dashboard_Example.jpg" class="" />
            </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
                </td>
                <td rowspan="1" colspan="1">
                </td>
                <td rowspan="1" colspan="1">
                </td>
        </tr>
</tbody>        </table>
            </div>
    <p>
    </p>
    </div>
    <div class="section-2"  id="121341710_DBQueryMonitorPlugin-PluginDetails"  >
        <h2>Plugin Details</h2>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Plug-In Versions    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="attachments_121569618_1_com.db.tabmon_2.0.0.jar">DB Query Monitor Plugin 2.0.0</a> (compatible with dynaTrace 4.2+)    </p>
    <p>
<a href="attachments_139657220_1_com.db.tabmon_3.1.0.jar">DB Query Monitor Plugin 3.1.0</a> (tested with 5.5)    </p>
    <p>
<a href="attachments_142541006_1_com.db.tabmon_3.2.0.jar">DB Query Monitor Plugin 3.2.0</a> (added support for Ingres 9.x and Ingres 10.x - tested with dT 5.5)    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Author    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Derek Abing (v 2.0.0)    </p>
    <p>
James Mackin (v.3.0.0+)    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
License    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="attachments_5275722_2_dynaTraceBSD.txt">dynaTrace BSD</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Support    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/DL/Support+Levels#SupportLevels-Community">Not Supported </a><br/>If you have any questions or suggestions for these plugins, please add a comment to this page, use our <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=46628918">forum</a>, or drop us an email at <a href="mailto:apmcommunity@compuware.com">apmcommunity@compuware.com</a>!    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Known Problems    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Release History    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
2013-05-23 Initial Release by Derek Abing    </p>
    <p>
2013-09-16 Version 3.0.0 provided by James Mackin now provides up to 10 result columns    </p>
    <p>
2013-09-23 Version 3.1.0 provided by James Mackin with addition configuration options and result measures    </p>
    <p>
2013-10-19 Version 3.2.0 provided by Steve Caron with support for Ingres 9.x &amp; 10.x    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
    
    <div class="section-2"  id="121341710_DBQueryMonitorPlugin-Configuration"  >
        <h2>Configuration</h2>
    
    <div class="tablewrap">
        <table>
<thead class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Name    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Value    </p>
            </td>
        </tr>
</thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQL Type    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Select if you want to query MSSQL, Oracle, or IBM DB2    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQL Port    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
SQL Port to use on the SQL Server    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Database    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Name of the database to connect to on the sql server    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQL Statement    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
SQL Statement to execute on the SQL database. The returned rows of this statement will be used for the monitor.    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Windows Logon    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
If checked, the program will use windows single sign-on and use the credentials associated with the Collector.    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQL Username    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Username to use for login to the database (SQL Server Authentication)    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
SQL Password    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Password for the username used to connect to the sql database (SQL Server Authentication)    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Capture Column Data    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
If this box is checked you can capture the value of the top row of a specific column. If this value is set, the query should ensure that the first record contains the pertinent data. The result set should also only contain one record, unless the row count is also important to trac    </span>
k.    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Query Timeout    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The query timeout period, in seconds. The query will be cancelled after this time and the total response time returned as a measure, which can then be alerted on    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Query Name    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
A readable short name used to identify the query in this monitor instance    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Column Name    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The name of the first column whose value should be returned. The returned value must be numeric    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Column Name 2    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The name of the second column whose value should be returned. The returned value must be numeric. If this column and any other subsequent columns are specified, then the values will be returned as dynamic measures, scoped by the Query Name    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Column Name 3 - 10    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The name of the 3rd - 10th columns whose values should be returned    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Match Content (since v 3.1.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
If this box is checked then two more configuration items become available to enter: a column name to compare against a constant value, and the constant value itself.    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Match Column (since v 3.1.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
The name of the column in the result set to match against    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Match Value (since v 3.1.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
The value, or regular expression, the column needs to be compared to. If the two values are equal the 'Content Verified' measure will be set to 1. Otherwise it will be set to 0.    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    <p>
    </p>
    <p>
***<strong class=" ">WARNING - The datatype of the column must be numeric!</strong>***    </p>
    </div>
    <div class="section-2"  id="121341710_DBQueryMonitorPlugin-Measures"  >
        <h2>Measures</h2>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Column Value    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
The value of the captured column as a regular measure. Will contain the value of the first column returned, if the <i class=" ">Capture Column Data</i> column is checked.    </p>
    <p>
If multiple column names are specified, then this measure can be selected and then split the measures based on the specified Query Name property    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
DB Connection    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Returns a 1 if the DB connection was successful and a 0 if the DB connection failed or if the query didn&rsquo;t finish within the Query Timeout number of seconds    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Row Count    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
Number of rows returned in the query    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Response Time (since v 3.0.0)    </span>
    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The total time, in milliseconds, that it took to connect to the database, execute the query, and close the database connection.    </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Connect Response Time    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The total time, in     <span style="color: #3d3d3d;">
milliseconds    </span>
, that it took to connect to the database.     </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
Query Response Time    </span>
 (since v 3.0.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    <span style="color: #3d3d3d;">
The total time, in     <span style="color: #3d3d3d;">
milliseconds    </span>
, that it took to execute the query.     </span>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Content Verified (since v 3.1.0)    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
1 if the Match Content flag is checked, and the Match Value and the contents of the Match Column are equivalent.    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
    <div class="section-2"  id="121341710_DBQueryMonitorPlugin-Installation"  >
        <h2>Installation</h2>
    <p>
Import the Plugin into the dynaTrace Server. For details how to do this please refer to the <a href="https://community/display/DOCDT50/Manage+and+Develop+User+Plugins">dynaTrace documentation</a>.    </p>
    </div>
            </div>
        </div>
        <div class="footer">
        </div>
    </div>
</body>
</html>
