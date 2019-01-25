environments(default: 'local') {
    //the default environment to be used. Can be overridden with System property environment eg mvn test -Denvironment='scrumqc'

    /**
     * Default Values for all environment
     * Can be overridden for each environment individually
     * Also all values can be overidden using system property, eg mvn test -Denvironment='scrumqc' -DrunOnGrid=false
     */
    defaultValues {
        runOnGrid 'true'
        hubUrl 'http://nvmiselhub1.d2hawkeye.net:4444/wd/hub'
        browser 'IE'
        waitTime '120'

        //groups and user configuration
        issuer 'www.webautomationtestissuer.com'
        ssoPrefix 'WEBAUTO_'
        maxPooledUsers 5
		
		//location to download files for report download tests
		fileDownloadLocation 'C:/Selenium/Downloads/'
        fileUploadLocation 'C:/Selenium/Uploads/'
        useShareLocationForFiles false

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

        skipTestThatRequireDatabase true
    }

    //Local Environment to be used for development
    environment(name: 'local') {
        appUrl 'https://mi-scmqa.verscend.com'
        adminUrl 'https://admin-scmqa.verscend.com/common/superUser/index.jsp'
        proxyTicketURL 'https://admin-scmqa.verscend.com/common/developer/proxyTicket.jsp'
        samlUrlToLogin 'https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp'
        demoerUrl 'http://nvscmjmsq1.d2hawkeye.net:9500'
        apiGatewayUrl 'https://nvmisvcgq1.d2hawkeye.net:2025'
        apiWidgetServiceEndpointUrl 'https://nvmisvcgq1.d2hawkeye.net:1800'
        apiERServiceEndpointUrl 'https://nvmisvcgq1.d2hawkeye.net:2020'
        eiDashboardUrl 'https://ei-scmqa.verscend.com'
        rmUrl 'https://rm-scmqa.verscend.com'
        mxappUrl 'http://nvscmlind2:8080'
        //appId '982-005'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        sfw_dynamic_cohortid '53080'
        sfw_static_cohortid '53081'
        dynamic_cohortid '53082'
        static_cohortid '53083'
        memberID '220011176'
        runOnGrid false
        browser 'CHROME'
        skipTestThatRequireDatabase false
		
		//location to download files for report download tests
        fileDownloadLocation 'C:/Selenium/Downloads/'
        fileUploadLocation 'C:/Selenium/Uploads/'
        useShareLocationForFiles false

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

        //database configuration required to create test users
        heuserUrl 'jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=yes)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=nvmidev03.d2hawkeye.net)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=scmdbq1)))'
        heuserUserName 'heuser'
        heuserPassword 'oracle'

        verticaUrl 'jdbc:vertica://VERTICAQAVIPA.D2HAWKEYE.NET:5543/DW_QC_1'
        verticaUsername 'Schemacreator'
        verticaPassword 'vtka14schma'
        beforeSchema 'S0954001_B'
        afterSchema 'S0954001_A'

        jobId 'local'
    }

    environment(name: 'scrumqc') {
        appUrl 'https://mi-scmqa.verscend.com'
        adminUrl 'https://admin-scmqa.verscend.com/common/superUser/index.jsp'
        proxyTicketURL 'https://admin-scmqa.verscend.com/common/developer/proxyTicket.jsp'
        samlUrlToLogin 'https://nvscmlinq1.d2hawkeye.net:8000/SAML/Response/index.jsp'
        demoerUrl 'http://nvscmjmsq1.d2hawkeye.net:9500'
        apiGatewayUrl 'https://nvmisvcgq1.d2hawkeye.net:2025'
        apiWidgetServiceEndpointUrl 'https://nvmisvcgq1.d2hawkeye.net:1800'
        apiERServiceEndpointUrl 'https://nvmisvcgq1.d2hawkeye.net:2020'
        eiDashboardUrl 'https://ei-scmqa.verscend.com'
        rmUrl 'https://rm-scmqa.verscend.com'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        memberID '220011176'
        sfw_dynamic_cohortid '53080'
        sfw_static_cohortid '53081'
        dynamic_cohortid '53082'
        static_cohortid '53083'
        skipTestThatRequireDatabase false

		//location to download files for report download tests
		fileDownloadLocation '//NVMISELHUB1/FileDownloadLocationForAllNodes/'
        fileUploadLocation '//NVMISELHUB1/FileUploadLocationForAllNodes/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

        //database configuration required to create test users
        heuserUrl 'jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=yes)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=nvmidev03.d2hawkeye.net)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=scmdbq1)))'
        heuserUserName 'heuser'
        heuserPassword 'oracle'

        //database configuration
        hedataUrl 'jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=yes)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=nvmidev03.d2hawkeye.net)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=scmdbq1)))'
        hedataUserName 'hedata'
    }

    environment(name: 'scrumdev') {
        appUrl 'https://mi-scmdev.verscend.com'
        adminUrl 'https://admin-scmdev.verscend.com/common/superUser/index.jsp'
        proxyTicketURL 'https://admin-scmdev.verscend.com/common/developer/proxyTicket.jsp'
        samlUrlToLogin 'https://nvscmlind1.d2hawkeye.net:8000/SAML/Response/index.jsp'
        demoerUrl 'http://nvscmjmsd1.d2hawkeye.net:9500'
        eiDashboardUrl 'https://ei-scmdev.verscend.com'
        rmUrl 'https://rm-scmdev.verscend.com'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        memberID '220011176'
        skipTestThatRequireDatabase false

        //location to download files for report download tests
        fileDownloadLocation '//NVMISELHUB1/FileDownloadLocationForAllNodes/'
        fileUploadLocation '//NVMISELHUB1/FileUploadLocationForAllNodes/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'
    }

    environment(name: 'mnsqc') {
        appUrl 'https://mi-mnsqa.verscend.com'
        adminUrl 'https://admin-mnsqa.verscend.com/common/superUser/index.jsp'
        demoerUrl 'http://nvmnshornetqq1.d2hawkeye.net:9500'
        eiDashboardUrl 'https://ei-mnsqa.verscend.com'
        rmUrl 'https://rm-mnsqa.verscend.com'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        memberID '220011176'
        skipTestThatRequireDatabase false

        //location to download files for report download tests
        fileDownloadLocation '//NVMISELHUB1/FileDownloadLocationForAllNodes/'
        fileUploadLocation '//NVMISELHUB1/FileUploadLocationForAllNodes/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

    }

    environment(name: 'mnsdev') {
        appUrl 'https://nvmimnsd01:8200'
        adminUrl 'https://nvmimnsd01:8100/common/superUser/index.jsp'
        demoerUrl 'http://nvmnshornetqd1.d2hawkeye.net:9500'
        eiDashboardUrl 'https://ei-mnsdev.verscend.com'
        rmUrl 'https://nvmimnsd01:8300'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        memberID '220011176'
        skipTestThatRequireDatabase false

        //location to download files for report download tests
        fileDownloadLocation '//NVMISELHUB1/FileDownloadLocationForAllNodes/'
        fileUploadLocation '//NVMISELHUB1/FileUploadLocationForAllNodes/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

    }

    environment(name: 'systemuat') {
        appUrl 'https://mi-uat.verscend.com/'
        adminUrl 'https://pophealthadmin-uat.verscend.com/common/superUser/index.jsp'
        samlUrlPrefix 'https://nvmia2.d2hawkeye.net:8200'
        samlUrlToLogin 'https://nvhydappu1.d2hawkeye.net:8000/SAML/Response/index.jsp'
        hubUrl 'http://nvmiselnod2.d2hawkeye.net:4444/wd/hub'
        demoerUrl 'http://nvhornetqa1.d2hawkeye.net:9500'
        eiDashboardUrl 'https://ei-scmuat.verscend.com'
        rmUrl 'https://reportmanager-uat.verscend.com'
        appId '981-001'
        appId2 '004-101'
        memberID '220011176'
        issuer 'www.testIssuer.com'
        ssoPrefix 'NNET_'
        skipTestThatRequireDatabase true
        jobId 'uat'

        fileDownloadLocation '//10.48.10.60/Downloads/'
        fileUploadLocation '//10.48.10.60/Uploads/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

        //database configuration required to create test users
        heuserUrl 'jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=yes)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=dm02mi-scan.d2hawkeye.net)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=MIUATSRV)))'
        heuserUserName 'heuser'

        hedataUrl 'jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=yes)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=dm02mi-scan.d2hawkeye.net)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=MIUATSRV)))'
        hedataUserName 'hedata'
    }

    environment(name: 'prodqc') {
        appUrl 'https://mi-qc.verscend.com'
        adminUrl 'https://pophealthadmin-qc.verscend.com/common/superUser/index.jsp'
        hubUrl 'http://nvmiselnod2.d2hawkeye.net:4444/wd/hub'
        demoerUrl 'http://nvhornetqqcp2.d2hawkeye.net:9500'
        eiDashboardUrl 'https://ei-mnsuat.verscend.com/'
        rmUrl 'https://reportmanager-qc.verscend.com'
        appId '981-001'
        appId2 '658-001'
        memberID '220011176'
        issuer 'www.testIssuer.com'
        ssoPrefix 'NNET_'
        skipTestThatRequireDatabase true
        jobId 'prodQC'

        fileDownloadLocation '//10.48.10.60/Downloads/'
        fileUploadLocation '//10.48.10.60/Uploads/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

    }

    environment(name: 'prod') {
        appUrl 'https://mi.verscend.com'
        adminUrl 'https://pophealthadmin.verscend.com/common/superUser/index.jsp'
        hubUrl 'http://nvmiselnod2.d2hawkeye.net:4444/wd/hub'
        demoerUrl 'http://nvhornetqp2.d2hawkeye.net:9500'
        eiDashboardUrl ''
        rmUrl 'https://reportmanager.verscend.com'
        appId '981-001'
        appId2 '008-201'
        memberID '220011176'
        issuer 'www.testIssuer.com'
        ssoPrefix 'NNET_'
        skipTestThatRequireDatabase true
        jobId 'prod'

        fileDownloadLocation '//10.48.10.60/Downloads/'
        fileUploadLocation '//10.48.10.60/Uploads/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'

    }

    environment(name: 'tomcatqc') {
        appUrl 'https://nvmiscmq01.d2hawkeye.net:8200'
        adminUrl 'https://nvmiscmq01.d2hawkeye.net:8100'
        demoerUrl 'http://nvscmjmsq2:9500'
        eiDashboardUrl 'https://ei-scmqa.verscend.com'
        rmUrl 'https://nvmiscmq01.d2hawkeye.net:8300'
        appId '981-001'
        appId2 '004-101'
        postingSchema 'S0981001_B'
        switchDatabase1 'S0004101_B'
        switchDatabase2 'S0004101_C'
        memberID '220011176'
        skipTestThatRequireDatabase false

        //location to download files for report download tests
        fileDownloadLocation '//NVMISELHUB1/FileDownloadLocationForAllNodes/'
        fileUploadLocation '//NVMISELHUB1/FileUploadLocationForAllNodes/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'
    }

    environment(name: 'tomcatuat') {
        appUrl 'https://mi-scmuat.verscend.com'
        adminUrl 'https://admin-scmuat.verscend.com'
        hubUrl 'http://nvmiselnod2.d2hawkeye.net:4444/wd/hub'
        demoerUrl 'http://nvhornetqa2:9500'
        eiDashboardUrl 'https://ei-scmuat.verscend.com'
        rmUrl 'https://rm-scmuat.verscend.com'
        appId '981-001'
        appId2 '004-101'
        memberID '220011176'
        issuer 'www.testIssuer.com'
        ssoPrefix 'NNET_'
        skipTestThatRequireDatabase true
        jobId 'uat'

        fileDownloadLocation '//10.48.10.60/Downloads/'
        fileUploadLocation '//10.48.10.60/Uploads/'
        useShareLocationForFiles true

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'
    }

    environment(name: 'demo') {
        appUrl 'https://products.veriskhealth.com'
        appId '659-001'
        appId2 '658-001'
        runOnGrid 'false'
        browser 'CHROME'
    }
}