environments(default: 'local') {
    //the default environment to be used. Can be overridden with System property environment eg mvn test -Denvironment='scrumqc'

    /**
     * Default Values for all environment
     * Can be overridden for each environment individually
     * Also all values can be overidden using system property, eg mvn test -Denvironment='qc' -DrunOnGrid=false
     */
    defaultValues {
        runOnGrid 'true'
        hubUrl ''
        browser 'CHROME'
        waitTime '120'

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
        appUrl 'https://www.google.com/'
        runOnGrid false
        browser 'CHROME'
        skipTestThatRequireDatabase false
		
		//location to download files for report download tests
        fileDownloadLocation 'C:/Selenium/Downloads/'
        fileUploadLocation 'C:/Selenium/Uploads/'
        useShareLocationForFiles false

        //User and password
        username ''
        password ''

        nodeDomain 'd2hawkeye'
        nodeUsername 'seleniumrun'
        nodePassword 'nepal888!'
        jobId 'local'
    }

    environment(name: 'qc') {
        appUrl ''
        skipTestThatRequireDatabase false

		//location to download files for report download tests
		fileDownloadLocation ''
        fileUploadLocation ''
        useShareLocationForFiles true

        nodeDomain ''
        nodeUsername ''
        nodePassword ''
    }

    environment(name: 'dev') {
        appUrl ''
        skipTestThatRequireDatabase false

        //location to download files for report download tests
        fileDownloadLocation ''
        fileUploadLocation ''
        useShareLocationForFiles true

        nodeDomain ''
        nodeUsername ''
        nodePassword ''
    }
}