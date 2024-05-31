package il.co.shivhit.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Global {

    private static int REQUEST_CAMERA = 0;
    private static int SELECT_FILE    = 1;

    public static void takePicture(Context context) {
        Global.takePicture(context, false);
    }

    public static void takePicture(Context context, boolean isHebrew) {
        String[] items = {"Take Photo", "Choose from Library", "Cancel"};
        String[] itemsHebrew = {"צילום תמונה", "בחירה מגלריה", "ביטול"};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setTitle((isHebrew) ? "תמונה" : "Photo")

                .setItems(((isHebrew) ? itemsHebrew : items), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Take photo
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) context).startActivityForResult(intent, REQUEST_CAMERA);
                        }
                        // Choose from gallery
                        else {
                            if (which == 1) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent = new Intent(Intent.ACTION_GET_CONTENT,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select photo"), SELECT_FILE);

                            }
                        }
                    }
                });

        builder.show();
    }
}
