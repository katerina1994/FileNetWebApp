<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="http://code.jquery.com/jquery-3.3.1.js"></script>
    <link  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="module" src="js/index.js"></script>
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <div class="media">
                    <div class="media-left">
                        <img src="img/img_avatar1.png" class="media-object" style="width:60px">
                    </div>
                    <div class="media-body">
                        <h4 class="media" id="employee"></h4>
                        <p></p>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-4">
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingPrint" onclick="">
                    </div>
                    <div class="panel-body" id="hierarchy">
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="panel panel-default">
                    <div class="panel-heading" id="printCurrentFolderName">
                    </div>
                    <div class="panel-body">
                        <div class="container" id="printChildren">
                        </div>
                        <div class="container" id="buttonOperations">
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="panel panel-default">
                    <div class="panel-heading" id="headingReportPrint">
                    </div>
                    <div class="panel-body" id="report">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="operationDialog" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header" >
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id = "operationDialogHeader"></h4>
                </div>
                <div class="modal-body" id = "operationDialogBody">

                    <form id="operationForm" class="form-horizontal" action=''>
                        <div class="form-group" id = "inputForm">
                            <label class="control-label col-sm-2" for="name">Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" placeholder="Enter name" name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Name</label>
                            <div class="col-sm-10">
                                <select class="selectpicker" multiple name="className">
                                    <option>Order</option>
                                    <option>Contract</option>
                                    <option>Statements</option>
                                </select>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type='button' id='submitForm' class="btn btn-default" value="Submit">
                            </div>
                        </div>
                    </form>
                    <div id="resultOperation">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>

    <div class="modal fade" id="operationDialogFile" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header" >
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id = "operationDialogFileHeader"></h4>
                </div>
                <div class="modal-body" id = "operationDialogFileBody">

                    <form id="operationFileForm" class="form-horizontal" action='' enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="Enter name" name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2">Document Type</label>
                            <div class="col-sm-10">
                                <select class="selectpicker" multiple name="className">
                                    <option>Order</option>
                                    <option>Contract</option>
                                    <option>Statements</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" >File</label>
                            <div class="col-sm-10">
                                <input type="file" class="btn" value name="filePath" id="exampleFormControlFile1">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type='button' id='submitFileForm' class="btn btn-default" value="Submit">
                            </div>
                        </div>
                    </form>
                    <div id="resultFileOperation">

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>

</body>
</html>