package com.example.youlu1803.Manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youlu1803.Adapter.CalllogAdpter;
import com.example.youlu1803.Adapter.ContactAdapter;
import com.example.youlu1803.Adapter.ConversationAdapter;

import com.example.youlu1803.Entity.Colllog;
import com.example.youlu1803.Entity.Contact;
import com.example.youlu1803.Entity.Conversation;

import com.example.youlu1803.R;


public class DialogManager {
    public static void ShowAddDialogContact(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();
        View dialogView = View.inflate(context, R.layout.contact_add_dialog_layout, null);
        dialog.setContentView(dialogView);
        ImageView CloseImageView = dialogView.findViewById(R.id.ImageView_Contact_Add_Dailog_Close);
        ImageView ComfrimImageView = dialogView.findViewById(R.id.ImageView_Contact_Add_Dailog_Comfrim);
        final EditText Name = dialogView.findViewById(R.id.EditText_Contact_Add_Name);
        final EditText Phone = dialogView.findViewById(R.id.EditText_Contact_Add_Phone);
        final EditText Email = dialogView.findViewById(R.id.EditText_Contact_Add_Email);
        final EditText Address = dialogView.findViewById(R.id.EditText_Contact_Add_Address);
        final EditText Company = dialogView.findViewById(R.id.EditText_Contact_Add_Company);
        CloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ComfrimImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String phone = Phone.getText().toString();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)){
                    Toast.makeText(context,"联系人姓名或者号码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                String email = Email.getText().toString();
                String address = Address.getText().toString();
                String company = Company.getText().toString();
                Intent intent = new Intent();
                intent.setAction(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT);
                Uri uri = Uri.parse("tel:"+phone);
                intent.setData(uri);
                intent.putExtra(ContactsContract.Intents.Insert.NAME,name);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL,email);
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL,address);
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY,company);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

    }
    public static void ShowEditContactDialog(final  Context context, final Contact contact){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();
        View dialogView = View.inflate(context, R.layout.contact_edit_dialog_layout, null);
        dialog.setContentView(dialogView);
        ImageView Photo = dialog.findViewById(R.id.Dialog_Contact_Eidt_Photo);
        ImageView CloseImageView = dialogView.findViewById(R.id.ImageView_Contact_Eidt_Dailog_Close);
        ImageView ComfrimImageView = dialogView.findViewById(R.id.ImageView_Contact_Eidt_Dailog_Eidt);
        final TextView Name = dialogView.findViewById(R.id.Dialog_Contact_Eidt_Name);
        final TextView Phone = dialogView.findViewById(R.id.Dialog_Contact_Eidt_Phone);
        final TextView Email = dialogView.findViewById(R.id.Dialog_Contact_Eidt_Email);
        final TextView Address = dialogView.findViewById(R.id.Dialog_Contact_Eidt_Address);
        CloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Name.setText(contact.getName());
        Phone.setText(contact.getPhone());
        if (!TextUtils.isEmpty(contact.getEmail())) {
            Email.setText(contact.getEmail());
        }else {
            Email.setText("");
        }
        if (!TextUtils.isEmpty(contact.getAddress())) {
            Address.setText(contact.getAddress());
        }else {
            Address.setText("");
        }
        Bitmap bitmap = ContactsManager.PhotobyPhotoId(context,contact.getPhotoId());
        bitmap = ImageManager.formatBitMap(context,bitmap);
        Photo.setImageBitmap(bitmap);

        ComfrimImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_EDIT);

                Uri uri = Uri.parse("content://contacts/people/"+contact.get_id());
                intent.setData(uri);
                intent.putExtra("finishActivityOnSaveCompleted",true);

                context.startActivity(intent);
                dialog.dismiss();
            }
        });

    }
    public static void ShowDeleteContactDialog(final Contact contact, final Context context, final ContactAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定删除联系人吗？");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsManager.DeleteContact(context,contact);
                adapter.RemoveData(contact);
                dialog.dismiss();
            }
        });
        builder.create().show();



    }
    public static void ShowDeleteCalllogDialog(final Colllog colllog, final Context context, final CalllogAdpter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定删除该通话记录吗？");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsManager.DeleteCalllog(context,colllog);
                adapter.RemoveData(colllog);
                dialog.dismiss();
            }
        });
        builder.create().show();



    }
    public static void ShowDeleteConversationDialog(final Conversation conversation, final Context context, final ConversationAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_warning);
        builder.setTitle("系统提示");
        builder.setMessage("确定删除该会话吗？");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("删除",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SMSManager.DeleteConversation(context,conversation.getThread_id());
                adapter.RemoveData(conversation);
                dialog.dismiss();
            }
        });
        builder.create().show();



    }
}
