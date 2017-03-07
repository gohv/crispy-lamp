package view;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.PlaceAdFragment;
import model.Ad;
import xyz.georgihristov.myadds.R;

/**
 * Created by gohv on 02.03.17.
 */
public class AddPhotos extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_LOAD = 2;
    public static final int xSize = 512;
    public static final int ySize = 384;
    private int buttonCode = 0;
    private String location;
    private Ad dataToSend;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imageView7)ImageView imageView7;
    @BindView(R.id.imageView8)ImageView imageView8;
    @BindView(R.id.imageView9)ImageView imageView9;
    @BindView(R.id.imageView10)ImageView imageView10;
    @BindView(R.id.imageView11)ImageView imageView11;
    @BindView(R.id.imageView12)ImageView imageView12;
    @BindView(R.id.button2)Button nextButton;
    private String galleryImage;
    private String cameraImage;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nextButton.setEnabled(false);

        dataToSend = (Ad) getIntent().getParcelableExtra(PlaceAdFragment.MY_AD);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddPhotos.this,CategoryActivity.class);
                intent.putExtra(PlaceAdFragment.MY_AD,dataToSend);
                Log.d("TEST",dataToSend.toString());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            galleryAddPic();

        }else if(requestCode == REQUEST_IMAGE_LOAD && resultCode == RESULT_OK){

            loadPic(data);

        }
    }

    private void loadPic(Intent data) {

        galleryImage = data.getData().toString();
        validateGalleryButton(galleryImage);


        dataToSend.setProductPhoto(galleryImage);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = saveImage();
            } catch (IOException ex) {

                Toast.makeText(this, R.string.message_error_saving, Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "xyz.myads.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private void dispatchGalleryIntent(){
        Intent intent = 
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_LOAD);
    }

    private void createDiaglog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Choose:");
        Button galleryButton = (Button) dialog.findViewById(R.id.galleryButton);
        Button cameraButton = (Button) dialog.findViewById(R.id.cameraButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchGalleryIntent();
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        dialog.show();
    }


    private File saveImage() throws IOException{

        String imageFileName = "AD" + new UUID(33,22) + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".png",
                storageDir
        );



        location = image.getAbsolutePath();

        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(location);
        cameraImage = Uri.fromFile(f).toString();
        mediaScanIntent.setData(Uri.parse(cameraImage));
        this.sendBroadcast(mediaScanIntent);
        dataToSend.setProductPhoto(cameraImage);
        validateCameraButton(cameraImage);
    }


    @OnClick(R.id.imageView7)void click(){
        createDiaglog();
        buttonCode = 7;
    }
    @OnClick(R.id.imageView8) void click1(){
        createDiaglog();
        buttonCode = 8;
    }
    @OnClick(R.id.imageView9) void click2(){
        createDiaglog();
        buttonCode = 9;
    }
    @OnClick(R.id.imageView10)void click3(){
        createDiaglog();
        buttonCode = 10;
    }
    @OnClick(R.id.imageView11) void click4(){
        createDiaglog();
        buttonCode = 11;
    }
    @OnClick(R.id.imageView12) void click5(){
        createDiaglog();
        buttonCode = 12;
    }
    private void enableNext(){
        nextButton.setEnabled(true);
    }

    private void validateGalleryButton(String image){
        if(buttonCode == 7){
            Picasso.with(this).load(image).into(imageView7);
            enableNext();
        }else if(buttonCode == 8){
            Picasso.with(this).load(image).into(imageView8);
            enableNext();
        }else if(buttonCode == 9){
            Picasso.with(this).load(image).into(imageView9);
            enableNext();
        }else if(buttonCode == 10){
            Picasso.with(this).load(image).into(imageView10);
            enableNext();
        }else if(buttonCode == 11){
            Picasso.with(this).load(image).into(imageView11);
            enableNext();
        }else if(buttonCode == 12){
            Picasso.with(this).load(image).into(imageView12);
            enableNext();
        }
    }
    private void validateCameraButton(String image) {
        /*!!! FORCING SIZE FOR PERFORMANCE !!!*/
        if(buttonCode == 7){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView7);
            enableNext();
        }else if(buttonCode == 8){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView8);
            enableNext();
        }else if(buttonCode == 9){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView9);
            enableNext();
        }else if(buttonCode == 10){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView10);
            enableNext();
        }else if(buttonCode == 11){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView11);
            enableNext();
        }else if(buttonCode == 12){
            Picasso.with(this).load(image).resize(xSize,ySize).into(imageView12);
            enableNext();
        }
    }

}
