package com.noxylem.signalreporting.navigation

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val REPORT_DATABASE = "report_database"

    const val REPORT_SITE_INFO = "report_site_info"
    const val REPORT_WORK = "report_work"
    const val REPORT_SERVICES = "report_services"
    const val REPORT_SIGNATURE = "report_signature"
    const val SIGNATURE_CAPTURE = "signature_capture"
    const val REPORT_TIMES = "report_times"

    const val IMAGE_SET = "image_set"
    const val CAMERA_CAPTURE = "camera_capture"
    const val IMAGE_MARKINGS = "image_markings"
    const val IMPORT_IMAGE = "import_image"

    const val ARG_REPORT_ID = "reportId"
    const val ARG_IMAGE_SET_ID = "imageSetId"
    const val ARG_IMAGE_ASSET_ID = "imageAssetId"

    fun reportSiteInfo(reportId: Long) = "$REPORT_SITE_INFO/$reportId"
    fun reportWork(reportId: Long) = "$REPORT_WORK/$reportId"
    fun reportServices(reportId: Long) = "$REPORT_SERVICES/$reportId"
    fun reportSignature(reportId: Long) = "$REPORT_SIGNATURE/$reportId"
    fun signatureCapture(reportId: Long) = "$SIGNATURE_CAPTURE/$reportId"
    fun reportTimes(reportId: Long) = "$REPORT_TIMES/$reportId"

    fun imageSet(imageSetId: Long, reportId: Long? = null): String {
        val linkedReport = reportId ?: 0L
        return "$IMAGE_SET/$imageSetId/$linkedReport"
    }

    fun cameraCapture(imageSetId: Long) = "$CAMERA_CAPTURE/$imageSetId"
    fun importImage(imageSetId: Long) = "$IMPORT_IMAGE/$imageSetId"
    fun imageMarkings(imageSetId: Long, imageAssetId: Long) =
        "$IMAGE_MARKINGS/$imageSetId/$imageAssetId"
}
