package fr.nomade.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var statusView: TextView

    private val cameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            inspectCameraCapabilities()
        } else {
            showStatus(
                "Autorisation caméra refusée. " +
                    "Elle est nécessaire pour examiner les possibilités de l’appareil."
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildScreen()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            inspectCameraCapabilities()
        } else {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun buildScreen() {
        statusView = TextView(this).apply {
            text = "Vérification des capacités de l’appareil…"
            textSize = 17f
            setPadding(32, 24, 32, 24)
        }

        val title = TextView(this).apply {
            text = "Nomade.app"
            textSize = 28f
            gravity = Gravity.CENTER
            setPadding(24, 32, 24, 12)
        }

        val subtitle = TextView(this).apply {
            text = "Vérification de la double caméra"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(24, 8, 24, 24)
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20)
            addView(title)
            addView(subtitle)
            addView(statusView)
        }

        setContentView(layout)
    }

    private fun inspectCameraCapabilities() {
        val systemSupportsConcurrentCamera =
            packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_CONCURRENT)

        if (!systemSupportsConcurrentCamera) {
            showStatus(
                "Android ne déclare pas la fonctionnalité de caméras simultanées " +
                    "sur cet appareil. Le fonctionnement à caméra unique restera disponible."
            )
            return
        }

        showStatus(
            "La fonctionnalité Android de caméras simultanées est annoncée. " +
                "Interrogation de CameraX…"
        )

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                val combinations = cameraProvider.availableConcurrentCameraInfos

                if (combinations.isEmpty()) {
                    showStatus(
                        "Android annonce la fonctionnalité, mais CameraX ne fournit " +
                            "aucune combinaison simultanée exploitable."
                    )
                } else {
                    showStatus(
                        "Résultat préliminaire positif : CameraX annonce " +
                            "${combinations.size} combinaison(s) de caméras simultanées.\n\n" +
                            "Cela ne prouve pas encore que deux enregistrements vidéo " +
                            "indépendants fonctionneront durablement. L’étape suivante " +
                            "consistera à ouvrir les deux caméras et à mesurer la stabilité."
                    )
                }
            } catch (exception: Exception) {
                showStatus(
                    "Impossible d’interroger CameraX : " +
                        (exception.localizedMessage ?: exception.javaClass.simpleName)
                )
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun showStatus(message: String) {
        statusView.text = message
    }
}
