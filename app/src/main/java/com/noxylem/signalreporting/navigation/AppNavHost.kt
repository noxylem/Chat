package com.noxylem.signalreporting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.screens.home.HomeScreen
import com.noxylem.signalreporting.ui.screens.imageset.CameraCaptureScreen
import com.noxylem.signalreporting.ui.screens.imageset.ImageMarkingsScreen
import com.noxylem.signalreporting.ui.screens.imageset.ImageSetScreen
import com.noxylem.signalreporting.ui.screens.imageset.ImportImageScreen
import com.noxylem.signalreporting.ui.screens.login.LoginScreen
import com.noxylem.signalreporting.ui.screens.login.LoginViewModel
import com.noxylem.signalreporting.ui.screens.report.ReportDatabaseScreen
import com.noxylem.signalreporting.ui.screens.report.ReportServicesScreen
import com.noxylem.signalreporting.ui.screens.report.ReportSignatureScreen
import com.noxylem.signalreporting.ui.screens.report.ReportSiteInfoScreen
import com.noxylem.signalreporting.ui.screens.report.ReportTimesScreen
import com.noxylem.signalreporting.ui.screens.report.ReportWorkScreen
import com.noxylem.signalreporting.ui.screens.report.SignatureCaptureScreen

@Composable
fun AppNavHost(factory: AppViewModelFactory) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val savedTechId by loginViewModel.savedTechId.collectAsState()
    val startDestination = if (savedTechId.isNullOrBlank()) Routes.LOGIN else Routes.HOME

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN) {
            LoginScreen(
                factory = factory,
                onLoggedIn = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                factory = factory,
                onCreateNewReport = { reportId ->
                    navController.navigate(Routes.reportSiteInfo(reportId))
                },
                onOpenReportDatabase = { navController.navigate(Routes.REPORT_DATABASE) },
                onCreateImageSet = { imageSetId ->
                    navController.navigate(Routes.imageSet(imageSetId))
                },
                onGoToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
            )
        }

        composable(Routes.REPORT_DATABASE) {
            ReportDatabaseScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "${Routes.REPORT_SITE_INFO}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) { entry ->
            val reportId = entry.arguments?.getLong(Routes.ARG_REPORT_ID) ?: return@composable
            ReportSiteInfoScreen(
                reportId = reportId,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.reportWork(reportId)) },
            )
        }

        composable(
            route = "${Routes.REPORT_WORK}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) { entry ->
            val reportId = entry.arguments?.getLong(Routes.ARG_REPORT_ID) ?: return@composable
            ReportWorkScreen(
                reportId = reportId,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.reportServices(reportId)) },
            )
        }

        composable(
            route = "${Routes.REPORT_SERVICES}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) { entry ->
            val reportId = entry.arguments?.getLong(Routes.ARG_REPORT_ID) ?: return@composable
            ReportServicesScreen(
                reportId = reportId,
                factory = factory,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.reportSignature(reportId)) },
                onOpenImageSet = { imageSetId ->
                    navController.navigate(Routes.imageSet(imageSetId, reportId))
                },
            )
        }

        composable(
            route = "${Routes.REPORT_SIGNATURE}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) { entry ->
            val reportId = entry.arguments?.getLong(Routes.ARG_REPORT_ID) ?: return@composable
            ReportSignatureScreen(
                reportId = reportId,
                onBack = { navController.popBackStack() },
                onOpenCapture = { navController.navigate(Routes.signatureCapture(reportId)) },
                onNext = { navController.navigate(Routes.reportTimes(reportId)) },
            )
        }

        composable(
            route = "${Routes.SIGNATURE_CAPTURE}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) {
            SignatureCaptureScreen(
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
        }

        composable(
            route = "${Routes.REPORT_TIMES}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType }),
        ) {
            ReportTimesScreen(
                onBack = { navController.popBackStack() },
                onFinish = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
            )
        }

        composable(
            route = "${Routes.IMAGE_SET}/{${Routes.ARG_IMAGE_SET_ID}}/{${Routes.ARG_REPORT_ID}}",
            arguments = listOf(
                navArgument(Routes.ARG_IMAGE_SET_ID) { type = NavType.LongType },
                navArgument(Routes.ARG_REPORT_ID) { type = NavType.LongType },
            ),
        ) { entry ->
            val imageSetId = entry.arguments?.getLong(Routes.ARG_IMAGE_SET_ID) ?: return@composable
            val reportArg = entry.arguments?.getLong(Routes.ARG_REPORT_ID) ?: 0L
            val reportId = reportArg.takeIf { it > 0 }
            ImageSetScreen(
                imageSetId = imageSetId,
                reportId = reportId,
                onBack = { navController.popBackStack() },
                onTakeImage = { navController.navigate(Routes.cameraCapture(imageSetId)) },
                onImportImage = { navController.navigate(Routes.importImage(imageSetId)) },
                onMarkingsStub = { navController.navigate(Routes.imageMarkings(imageSetId, 0L)) },
            )
        }

        composable(
            route = "${Routes.CAMERA_CAPTURE}/{${Routes.ARG_IMAGE_SET_ID}}",
            arguments = listOf(navArgument(Routes.ARG_IMAGE_SET_ID) { type = NavType.LongType }),
        ) { entry ->
            val imageSetId = entry.arguments?.getLong(Routes.ARG_IMAGE_SET_ID) ?: return@composable
            CameraCaptureScreen(
                imageSetId = imageSetId,
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = "${Routes.IMPORT_IMAGE}/{${Routes.ARG_IMAGE_SET_ID}}",
            arguments = listOf(navArgument(Routes.ARG_IMAGE_SET_ID) { type = NavType.LongType }),
        ) { entry ->
            val imageSetId = entry.arguments?.getLong(Routes.ARG_IMAGE_SET_ID) ?: return@composable
            ImportImageScreen(
                imageSetId = imageSetId,
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = "${Routes.IMAGE_MARKINGS}/{${Routes.ARG_IMAGE_SET_ID}}/{${Routes.ARG_IMAGE_ASSET_ID}}",
            arguments = listOf(
                navArgument(Routes.ARG_IMAGE_SET_ID) { type = NavType.LongType },
                navArgument(Routes.ARG_IMAGE_ASSET_ID) { type = NavType.LongType },
            ),
        ) { entry ->
            val imageSetId = entry.arguments?.getLong(Routes.ARG_IMAGE_SET_ID) ?: return@composable
            val assetId = entry.arguments?.getLong(Routes.ARG_IMAGE_ASSET_ID) ?: return@composable
            ImageMarkingsScreen(
                imageSetId = imageSetId,
                imageAssetId = assetId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
