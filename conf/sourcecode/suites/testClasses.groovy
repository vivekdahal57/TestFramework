import com.vh.mi.automation.groovy.suitegenerator.SuiteBuilder

/**
 * Created by i81131 on 10/5/2015.
 */

builder = new SuiteBuilder()
builder.part = System.getProperty("part", "1")
builder.suites = Integer.valueOf(System.getProperty("suites", "1"))
builder.parallel = System.getProperty("parallel", "true")
builder.threads = Integer.valueOf(System.getProperty("threads", "1"))

builder.testConfiguration {
    configuration {
        runOnParallel = true
        parallel = 'instances'
        numThreads = 1
    }

    tests {
        defaultTests{
            /**
             * Report Generation
             */
            //test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.claimsSearch.ClaimsSearchExtractGenerationTest'}
            test {testClass 'com.vh.mi.automation.test.pages.reportManager.GenerateMIRReportTest' }

            /**
             * claimsmodule
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01RBTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01DataGridSortingTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01DataGridFilteringTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01BLCompTest'}
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01APTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01CheckSendToCSVTest'}*/
            /* test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01SendToExcelTest'} */
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.claims.Claims01DrillTest'}*/

            /**
             * customperformacemeasures
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures.CPM01PaginationTest' }
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures.CPM01DataGridCustomizerTest' }*/
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.customPerformanceMeasures.CPM01VerifyListCreationTest' }*/

            /**
             * demography
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.D07AgeGroupPaginationTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.DemographyD05BLComponentTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.DemographyD05PaginationTest' }
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.DemographyD05RBTest'}*/
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.DemographyD05APTest' }*/
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.demography.DemographyD05DataGridCustomizationTest' }*/

            /**
             * diseaseregistry
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.diseaseRegistry.DiseaseRegistryCD016BLComponentTest' }
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.diseaseRegistry.drill.ComorbidityCD016Test'}*/
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.diseaseRegistry.DiseaseRegistryCD016AdjustedNormTest' }*/
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.diseaseRegistry.DiseaseRegistryCD016APTest' }*/


            /**
             * executivesummary
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.executiveSummary.ExecutiveSummaryAdjustedNormTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.executiveSummary.ExecutiveSummaryBLComponentTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.executiveSummary.ExecutiveSummaryRBTest' }
            /*test {testClass 'com.vh.mi.automation.test.pages.analytics.executiveSummary.ExecutiveSummaryAPTest' }*/

            /**
             * expensedistribution
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.expenseDistribution.ExpenseDistributionAPTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.expenseDistribution.ExpenseDistributionBLComponentTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.expenseDistribution.ExpenseDistributionDataGridCustomizationTest' }

            /**
             * hospitalprofiler
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.hospitalProfiler.HospitalProfilesHP100BLComponentTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.hospitalProfiler.HospitalProfilesHP100PaginationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.hospitalProfiler.HospitalProfilesHP120PaginationTest'}
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.hospitalProfiler.HospitalProfilesHP100DataGridCustomizationTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.hospitalProfiler.HospitalProfilesHP100APTest'}*/

            /**
             *individuals
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.IndvClaimDetail949DataGridCustomizationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301SummaryTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301PaginationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301DataGridDrillTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301DataGridCustomizationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301BLComponentTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301APTest'}
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301SendToExcelTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301QualityMeasuresTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301ExportIndvClaimsDetailsTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.IndvStaticMemberListTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.IndvDynamicMemberListTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301RBTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301LetterGenerationTests'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301HRAPageTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301HASPageTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301FavQualityMeasuresTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301ExportToCSVTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301DataGrid_SortingTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.individuals.Indv301DataGrid_FilteringTest'}*/
            /**
             * networkutilization
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.networkUtilization.NetworkUtilizationNU105APTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.networkUtilization.NetworkUtilizationNU105BLComponentTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.networkUtilization.NetworkUtilizationNU105RBTest'}

            /**
             * populationdashboard
             */
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.PopulationDashboard.Summary.SummaryPDFTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.PopulationDashboard.Trend.TrendPDFTest'}*/

            /**
             * populationriskdriver
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.populationriskdriver.PRD01CostDataGridDrillTest'}

            /**
             * qualityandrisk
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QualityAndRiskQRMTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QualityAndRiskPageValidationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QualityAndRiskDataGridSortingTest'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QualityAndRiskBLComponentTest'}
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QRMStaticMemberListTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.analytics.qualityAndRisk.QualityAndRiskSendToExcelTest'}*/

            /**
             * utilizationmetrics
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.utilizationMetrics.UtilizationMetricsBLComponentTest' }
            test {testClass 'com.vh.mi.automation.test.pages.analytics.utilizationMetrics.UtilizationMetricsHM50APTest'}

            /**
             * querybuilder
             */
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StratifierCohortCreationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.provider.SpecialtyTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.provider.ProviderTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.expert.MVCAQueryCriteriaAndTableValidationTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.expert.MVCAExpert301EAPTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.expert.MVCAExpert301E_RBTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.expert.MVCAExpert301BLComponentTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.basic.MVCABasic310B_RBTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.basic.MVCABasic301BLComponentTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.basic.MVCABasic301BAPTest'}
            test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.mvca.basic.MVCABasic301AnalysisConditionTest'}
            //test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.claimsSearch.MVCADynamicMemberListTest'}
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.claimsSearch.ClaimsSearchExtractFromMVCAMemberList'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.procedure.ProcedureGroupTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.procedure.ProcedureCodeTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.diagnosis.DiagnosisGroupTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.diagnosis.DiagnosisCodeTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StratifierStaticCohortAnd301MemberListTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StratifierFunctionalitesTests'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StratifierComponentsRelatedTests'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StratifierCohortAndSFWTest'}*/
            /*test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.stratifier.StartifierPopulationAnalyzerViewQueryDetailsTest'}*/
            /**
             * mirreport
             */
            test {testClass 'com.vh.mi.automation.test.pages.reportManager.GenerateMIRReportTest' }
            test {testClass 'com.vh.mi.automation.test.pages.reportManager.DownloadMIRReportTest' }

        }
        specialTests{
            /**
             * conversionanalyzer
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17APTest'
                excludeIn '149-001',	'149-002',	'300-006',	'300-008',	'661-001',	'663-001',	'693001',	'739-002',	'740-002',	'790-001',	'840-001',	'862-001',	'864-001',	'896-001',	'904-001',	'910-001',	'914-001',	'926-001',	'928-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17BLComponentTest'
                excludeIn '149-001',	'149-002',	'300-006',	'300-008',	'661-001',	'663-001',	'693001',	'739-002',	'740-002',	'790-001',	'840-001',	'862-001',	'864-001',	'896-001',	'904-001',	'910-001',	'914-001',	'926-001',	'928-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17DataGridFilteringTest'
                excludeIn '149-001',	'149-002',	'300-006',	'300-008',	'661-001',	'663-001',	'693001',	'739-002',	'740-002',	'790-001',	'840-001',	'862-001',	'864-001',	'896-001',	'904-001',	'910-001',	'914-001',	'926-001',	'928-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17DataGridSortingTest'
                excludeIn '149-001',	'149-002',	'300-006',	'300-008',	'661-001',	'663-001',	'693001',	'739-002',	'740-002',	'790-001',	'840-001',	'862-001',	'864-001',	'896-001',	'904-001',	'910-001',	'914-001',	'926-001',	'928-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.conversionAnalyzer.ConversionAnalyzerA17PaginationTest'
                excludeIn '149-001',	'149-002',	'300-006',	'300-008',	'661-001',	'663-001',	'693001',	'739-002',	'740-002',	'790-001',	'840-001',	'862-001',	'864-001',	'896-001',	'904-001',	'910-001',	'914-001',	'926-001',	'928-001'}

            /**
             * providermanagement
             */
            test { testClass 'com.vh.mi.automation.test.pages.analytics.providerManagement.physicianManager.ProfilerDashboardTest'
                includeOnlyIn '149-001',	'149-002',	'300-008',	'425-001',	'433-001',	'659-001',	'661-001',	'705-002',	'716-001',	'716-002',	'730-001',	'736-001',	'740-002',	'742-001',	'752-001',	'785-001',	'788-001',	'790-001',	'803-001',	'813-001',	'825-001',	'833-001',	'840-001',	'843-001',	'864-001',	'867-001',	'870-001',	'898-001',	'910-001',	'913-001',	'913-002',	'918-001',	'920-001',	'926-001',	'928-001',	'930-001'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.providerManagement.clinicManager.CP100DrillTest'
                includeOnlyIn '149-001',	'149-002',	'300-008',	'425-001',	'433-001',	'659-001',	'661-001',	'705-002',	'716-001',	'716-002',	'730-001',	'736-001',	'740-002',	'742-001',	'752-001',	'785-001',	'788-001',	'790-001',	'803-001',	'813-001',	'825-001',	'833-001',	'840-001',	'843-001',	'864-001',	'867-001',	'870-001',	'898-001',	'910-001',	'913-001',	'913-002',	'918-001',	'920-001',	'926-001',	'928-001',	'930-001'}

            /**
             * snfprofiler
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.snfProfiler.SNFProfilesSP120PaginationTest'
                includeOnlyIn '009-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.snfProfiler.SNFProfilesSP120PaginationTest'
                includeOnlyIn '009-001'}

            /**
             * spikes
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.spikes.SpikesS110BAPTest'
                excludeIn '149-001',	'149-002',	'693001',	'739-002',	'740-002',	'752-001',	'762-001',	'790-001',	'795-001',	'813-001',	'814-001',	'824-001',	'833-001',	'840-001',	'842-001',	'862-001',	'864-001',	'867-001',	'872-001',	'904-001',	'910-001',	'913-001',	'913-002',	'914-001',	'917-001',	'920-001',	'926-001',	'928-001',	'930-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.spikes.SpikesS110BBLComponentTest'
                excludeIn '149-001',	'149-002',	'693001',	'739-002',	'740-002',	'752-001',	'762-001',	'790-001',	'795-001',	'813-001',	'814-001',	'824-001',	'833-001',	'840-001',	'842-001',	'862-001',	'864-001',	'867-001',	'872-001',	'904-001',	'910-001',	'913-001',	'913-002',	'914-001',	'917-001',	'920-001',	'926-001',	'928-001',	'930-001'}

            /**
             * providerprofiler
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.providerProfiler.ProviderProfilerDataGridDrillTest'
                excludeIn '149-001',	'149-002',	'693001',	'716-001',	'716-002',	'726-001',	'730-001',	'739-002',	'740-002',	'742-001',	'762-001',	'790-001',	'803-001',	'862-001',	'864-001',	'910-001',	'914-001',	'926-001',	'930-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.providerProfiler.ProviderProfilerV044BLComponentTest'
                excludeIn '149-001',	'149-002',	'693001',	'716-001',	'716-002',	'726-001',	'730-001',	'739-002',	'740-002',	'742-001',	'762-001',	'790-001',	'803-001',	'862-001',	'864-001',	'910-001',	'914-001',	'926-001',	'930-001'}
            //test {testClass 'com.vh.mi.automation.test.pages.analytics.providerProfiler.ProviderProfilerDynamicMemberListTest'
              //  excludeIn '149-001',	'149-002',	'693001',	'716-001',	'716-002',	'726-001',	'730-001',	'739-002',	'740-002',	'742-001',	'762-001',	'790-001',	'803-001',	'862-001',	'864-001',	'910-001',	'914-001',	'926-001',	'930-001'}
            test {testClass 'com.vh.mi.automation.test.pages.analytics.providerProfiler.ProviderProfilerV044APTest'
                excludeIn '149-001',	'149-002',	'693001',	'716-001',	'716-002',	'726-001',	'730-001',	'739-002',	'740-002',	'742-001',	'762-001',	'790-001',	'803-001',	'862-001',	'864-001',	'910-001',	'914-001',	'926-001',	'930-001'}

            /**
             *inoutnetworkanalyzer
             */
            test {testClass 'com.vh.mi.automation.test.pages.analytics.networkAnalyzer.DashboardPageTest'
                includeOnlyIn '149-001',	'149-002',	'300-008',	'425-001',	'433-001',	'659-001',	'661-001',	'714-001',	'716-001',	'716-002',	'717-001',	'730-001',	'731-001',	'731-004',	'736-001',	'752-001',	'785-001',	'786-001',	'787-001',	'788-001',	'790-001',	'795-001',	'797-001',	'813-001',	'814-001',	'814-002',	'824-001',	'825-001',	'833-001',	'835-001',	'838-001',	'842-001',	'843-001',	'851-001',	'867-001',	'868-001',	'870-001',	'872-001',	'878-001',	'898-001',	'903-001',	'910-001',	'913-001',	'913-002',	'917-001',	'918-001',	'920-001',	'926-001',	'928-001',	'930-001'}
            test { testClass 'com.vh.mi.automation.test.pages.analytics.networkAnalyzer.DetailedAnalyticsOU107APTest'
                includeOnlyIn '149-001',	'149-002',	'300-008',	'425-001',	'433-001',	'659-001',	'661-001',	'714-001',	'716-001',	'716-002',	'717-001',	'730-001',	'731-001',	'731-004',	'736-001',	'752-001',	'785-001',	'786-001',	'787-001',	'788-001',	'790-001',	'795-001',	'797-001',	'813-001',	'814-001',	'814-002',	'824-001',	'825-001',	'833-001',	'835-001',	'838-001',	'842-001',	'843-001',	'851-001',	'867-001',	'868-001',	'870-001',	'872-001',	'878-001',	'898-001',	'903-001',	'910-001',	'913-001',	'913-002',	'917-001',	'918-001',	'920-001',	'926-001',	'928-001',	'930-001'}

            /**
             * Report Download
             */
            test {testClass 'com.vh.mi.automation.test.pages.reportManager.DownloadMIRReportTest' }
            //test { testClass 'com.vh.mi.automation.test.pages.queryBuilder.claimsSearch.ClaimsSearchExtractDownloadTest'}

        }

    }
}
builder.createSuiteXML()