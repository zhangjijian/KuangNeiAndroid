package me.kuangneipro.activity;

import java.util.ArrayList;
import java.util.List;

import me.kuangneipro.R;
import me.kuangneipro.Adapter.PostingImageAdapter;
import me.kuangneipro.core.HttpActivity;
import me.kuangneipro.entity.ChannelEntity;
import me.kuangneipro.entity.PostingInfo;
import me.kuangneipro.entity.UploadImage;
import me.kuangneipro.manager.PostEntityManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

public class PostingActivity extends HttpActivity{
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final String TAG = PostingActivity.class.getSimpleName();
	
	private EditText mEditText;
	private GridView mImageGrid;
	private MenuItem mSendPostButton;
	
	private ChannelEntity mChannel;
	private PostingInfo mPostingInfo;
	
	private final List<UploadImage> mUploadImages;
	private PostingImageAdapter mPostingImageAdapter;
	
	public PostingActivity(){
		mUploadImages = new ArrayList<UploadImage>();
		mPostingInfo = new PostingInfo();
		mPostingInfo.setUploadImage(mUploadImages);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posting);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_holo_dark);
		
		mChannel = (ChannelEntity)getIntent().getParcelableExtra(PostListActivity.SELECT_CHANNEL_INFO);
		mPostingInfo.setChannel(mChannel);
		
		mEditText = (EditText) findViewById(R.id.editTextPost);
		mImageGrid = (GridView) findViewById(R.id.imageGrid);
		mPostingImageAdapter = new PostingImageAdapter(this, mUploadImages);
		mImageGrid.setAdapter(mPostingImageAdapter);
		ImageButton imgBtnChoose = (ImageButton) findViewById(R.id.imgBtnChoose);
		imgBtnChoose.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
	    });
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
  
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            Log.i(TAG, "choice picture:" + picturePath);
            mUploadImages.add(new UploadImage(picturePath));
            mPostingImageAdapter.notifyDataSetChanged();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.posting, menu);
		mSendPostButton = menu.findItem(R.id.action_post);
		return true;
	}
	
	private void sendPost() {
    	String message = mEditText.getText().toString();
    	if (message.isEmpty()) {
    		String warnning = this.getString(R.string.info_post_empty);
    		Toast.makeText(this, warnning, Toast.LENGTH_LONG).show();
    		setPostingButtonEable(true);
    	} else {
    		mPostingInfo.setContent(message);
    		PostEntityManager.doPostingTotal(this,mPostingInfo);
    		finish();
    	}
	}
	
	private void setPostingButtonEable(boolean enable){
		if(mSendPostButton!=null)
			mSendPostButton.setEnabled(enable);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_post:
			setPostingButtonEable(false);
			sendPost();
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
