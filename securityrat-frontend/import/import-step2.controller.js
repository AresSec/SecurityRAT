'use strict';

angular.module('sdlctoolApp')
    .controller('ImportAssistantStep2Controller', function ($scope, Import, $state, ExcelHelper) {
        $scope.entryType = "row-based";
        $scope.table = Import.getWorksheet();
        $scope.mappings = [];

        $scope.mappingBox = {
            entityTypes: [],
            selectedEntityIdentifier: null,
            attributeMappings: [],

            addMapping: function () {
                $scope.mappingBox.attributeMappings.push(new Object());
            },

            isReference: function (attrIdentifier) {
                var attr = this.selectedEntity.attributes.find(function (attr) {
                    return attr.identifier === attrIdentifier
                });

                return attr && attr.type.reference;
            },

            getEntityMappings: function (attrIdentifier) {
                var attr = this.selectedEntity.attributes.find(function (attr) {
                    return attr.identifier === attrIdentifier
                });

                if (!attr) {
                    return [];
                }

                return $scope.mappings.filter(function (mapping) {
                    return mapping.type === attr.type.referenceIdentifier;
                });
            },

            getEntryList: function () {
                var result = new Array();

                if ($scope.entryType === 'column-based') {
                    for (var i = 0; i < $scope.table.rows; i++) {
                        result.push('Row ' + (i + 1));
                    }
                } else {
                    for (var i = 0; i < $scope.table.columns; i++) {
                        result.push('Column ' + ExcelHelper.indexToColumn(i));
                    }
                }

                return result;
            },

            confirm: function () {
                $scope.mappings.push({
                    id: Import.randomId(),
                    type: $scope.mappingBox.selectedEntityIdentifier,
                    attributes: $scope.mappingBox.attributeMappings
                });

                $scope.mappingBox.attributeMappings = [];
            }
        };

        Object.defineProperty($scope.mappingBox, "selectedEntity", {
            get: function () {
                if (!$scope.mappingBox.selectedEntityIdentifier) {
                    return;
                }

                return $scope.mappingBox.entityTypes.find(function (value) {
                    return value.identifier === $scope.mappingBox.selectedEntityIdentifier;
                });
            }
        });

        $scope.cancel = function () {
            Import.reset();
            $state.go('editor');
        };

        $scope.confirm = function () {
            Import.setMappings($scope.mappings);
            $state.go('importAssistantStep3');  
        };

        // Utility functions

        $scope.repeatArray = function (size) {
            var result = new Array();

            for (var i = 0; i < size; i++) {
                result.push(i);
            }

            return result;
        };

        $scope.getColumnHeader = function (index) {
            return ExcelHelper.indexToColumn(index);
        };

        $scope.getMappingDisplayName = function (mappingId) {
            var mapping = $scope.mappings.find(function (mapping) {
                return mapping.id === mappingId;
            });

            if (!mapping) {
                return '';
            }

            var entityType = $scope.mappingBox.entityTypes.find(function (type) {
                return type.identifier === mapping.type;
            });

            if (!entityType) {
                return '';
            }

            return entityType.displayName + ' (' + mapping.id + ')';
        };

        // Loading the entity types

        Import.getEntityTypes()
            .then(function (types) {
                $scope.mappingBox.entityTypes = types;

                // Selecting the first entity of the array
                //
                // NOTE: Empty array handling.

                $scope.mappingBox.selectedEntityIdentifier =
                    types[0].identifier;
            });

        // Initializing the DataTable view after AngularJS is ready

        angular.element(document).ready(function () {
            $('#table-view').DataTable({
                scrollX: true,
                scrollY: true,
                pageLength: 3,
                lengthMenu: [ 1, 2, 3, 5, 10, 20, 50 ]
            });
        });
    });
