'use strict';

angular.module('sdlctoolApp')
    .controller('ImportAssistantStep1Controller', function ($scope, Import, $state) {
        var _fileChooserInput = null;
        var _file = null;
        var _parsedWorkbook = null;

        $scope.fileBox = {
            selectedWorksheet: null,
            handleDragover: function (event) {
                // We need to cancel the default behavior of the browser
                // because otherwise it will change the cursor

                event.preventDefault();
            },
            handleDrop: function (event) {
                // Since we accept the dropped file, we want to prevent the
                // browser's default behavior.

                event.preventDefault();

                // Attempting to get the dataTransfer instance of the
                // originalEvent. (Not all browsers do support this.)

                var dataTransfer = ('dataTransfer' in event.originalEvent)
                    ? event.originalEvent.dataTransfer
                    : null;

                if (!dataTransfer || !dataTransfer.files) {
                    // Dropping files is not supported.
                    return;
                }

                if (dataTransfer.files.length < 1) {
                    // The dropped object was not a file.
                    return;
                }

                // Since we do not support importing data from multiple files
                // at the same time, we just grab the first entry of the file
                // array.

                $scope.fileBox.file = dataTransfer.files[0];
            },
            handleClick: function () {
                // We only open the file chooser dialog, if no file has been
                // selected already.

                if ($scope.fileBox.file) {
                    return;
                }

                // If the file chooser has been used already, we remove the old
                // input field and create a new one.
                //
                // This is required because we cannot catch a file chooser
                // cancelation but should not add to many elements to the DOM
                // (especially because circumvent AngularJS with this).

                if (_fileChooserInput) {
                    _fileChooserInput.remove();
                    _fileChooserInput = null;
                }

                // There is only one way to open a file chooser dialog:
                // Clicking on a <input type="file"> element.

                _fileChooserInput = $('<input type="file" accept=".csv,.xls,.xlsx" style="visibility: hidden;">');

                _fileChooserInput.on('change', function () {
                    var input = _fileChooserInput.get(0);

                    // Not all browsers support the File API, but the current
                    // do.

                    if (input.files && input.files.length > 0) {
                        // Again, we just grab the first file because we do not
                        // support processing multiple ones at the same time.

                        $scope.$apply(function () {
                            $scope.fileBox.file = input.files[0];
                        });
                    }
                });

                _fileChooserInput.appendTo(document.body);
                _fileChooserInput.click();
            },
            reset: function () {
                $scope.fileBox.file = null;
            }
        };

        Object.defineProperty($scope.fileBox, "file", {
            get: function () {
                return _file;
            },
            set: function (value) {
                if (!value) {
                    _file = null;
                    _parsedWorkbook = null;
                    return;
                }

                // Reading and parsing the file to access its meta data

                var reader = new FileReader();

                reader.onload = function (event) {
                    var workbook;

                    // Parsing the data with sheetjs

                    try {
                        workbook = XLSX.read(
                            new Uint8Array(event.target.result),
                            { type: 'array' });
                    } catch (ex) {
                        return;
                    }

                    $scope.$apply(function () {
                        _file = value;
                        _parsedWorkbook = workbook;

                        // By default, we select the first sheet.
                        //
                        // NOTE: Null-check not required because any workbook
                        //       always consists of at least one sheet.

                        $scope.fileBox.selectedWorksheet =
                            _parsedWorkbook.SheetNames[0];
                    });
                };

                reader.readAsArrayBuffer(value);
            }
        });

        Object.defineProperty($scope.fileBox, "sheets", {
            get: function () {
                if (!_parsedWorkbook) {
                    return;
                }

                return _parsedWorkbook.SheetNames;
            }
        });

        $scope.confirm = function () {
            // Storing things at the service and continuing with the next page.

            Import.setInput(_parsedWorkbook, $scope.fileBox.selectedWorksheet);
            $state.go('importAssistantStep2');
        };
    });
