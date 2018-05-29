package projetannuel.bigteam.com

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var scannerView: ZXingScannerView

    companion object {
        const val scanCodeFlag = "qrCodeValue"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
    }

    override fun handleResult(data: Result?) {
        data?.let {
            Log.v("@test", it.text)
            scannerView.resumeCameraPreview(this)
            val intent = Intent()
            intent.putExtra(QRCodeActivity.scanCodeFlag, it.text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

}
