package zsmith.capstone.whileimout;

import java.io.IOException;
import java.util.logging.FileHandler;

import android.app.Activity;

import com.google.gson.Gson;

public class HomeActivity extends Activity {
	
	Gson gson;
	FileHandler log;
	
	public HomeActivity() throws Exception {
		gson = new Gson();
		
		try {
			log = new FileHandler("log.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
////		setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(),
////                R.array.tut_titles, R.layout.list_item	));
////		final String[] links = getResources().getStringArray(R.array.tut_links);
////		getListView().setOnItemClickListener(new OnItemClickListener(){
//////			@Override
////			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
////				String content = links[position];
////				Intent showContent = new Intent(getApplicationContext(), TutViewerActivity.class);
////				showContent.setData(Uri.parse(content));
////				startActivity(showContent);
////			}
////		});
//	}
    
}
