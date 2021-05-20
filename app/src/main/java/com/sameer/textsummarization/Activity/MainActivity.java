package com.sameer.textsummarization.Activity;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.sameer.textsummarization.Classes.MyRetro;
import com.sameer.textsummarization.R;
import java.io.IOException;



public class MainActivity extends AppCompatActivity{


    TextView FilePathShow;
    public static TextView Summary;
    private String Text_Inside_PDF;

    private static final String PRIMARY = "primary";
    private static final String LOCAL_STORAGE = "/storage/self/primary/";
    private static final String EXT_STORAGE = "/storage/733E-10F6/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilePathShow = findViewById(R.id.filepathshow);
        Summary = findViewById(R.id.summary);
        FloatingActionButton selectFile = findViewById(R.id.selectfile);
        Summary.setMovementMethod(new ScrollingMovementMethod());

        // Getting user permission for external storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

       selectFile.setOnClickListener(view -> {
           Toast.makeText(this, "Select PDF File from SD Card!", Toast.LENGTH_SHORT).show();
           Intent MyFileIntent = null;
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
               MyFileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
           }
           assert MyFileIntent != null;
           MyFileIntent.setType("application/pdf");
         startActivityForResult(MyFileIntent,10);

       });





    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    ReadPdfFile(uri);
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void ReadPdfFile(Uri uri){
        String FullPath;
        if(uri.getPath().contains(PRIMARY)){
            FullPath = LOCAL_STORAGE + uri.getPath().split(":")[1];
        }
        else {
            FullPath = EXT_STORAGE + uri.getPath().split(":")[1];
        }

        StringBuilder StringParser = new StringBuilder();
        try {
            PdfReader pdfReader = new PdfReader(FullPath);
            int no_of_pages = pdfReader.getNumberOfPages();
            for (int i = 1;i <= no_of_pages;i++){
                StringParser.append(PdfTextExtractor.getTextFromPage(pdfReader, i).trim());
            }
            pdfReader.close();
            Text_Inside_PDF = StringParser.toString();
            Log.d("Text", "ReadPdfFile: " + Text_Inside_PDF.toString());
            new MyRetro("http://13.234.204.151:8888/").execute(Text_Inside_PDF);
            FilePathShow.setText("FilePath: " + FullPath);
            //Summary.setText("Summary: "+ StringParser.toString());

        } catch (IOException e) {
            Toast.makeText(this, "Please Select a PDF File From Your SD Card!", Toast.LENGTH_SHORT).show();
        }
    }
}

    


