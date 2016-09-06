package kristech.recyclerviewandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kristech.Utils.DividerItemDecoration;
import kristech.Utils.LogUtils;
import kristech.adapter.ModelAdapter;
import kristech.dto.ModelDTO;
import kristech.readresponses.RecyclerClickListener;
import kristech.readresponses.StringResponse;
import kristech.requests.SimpleStringRequest;
import kristech.requests.SingletonInstance;

/**
 * This example sample has taken reference from: http://www.truiton.com/2015/02/android-recyclerview-tutorial/
 */
public class MainActivity extends AppCompatActivity implements StringResponse, RecyclerClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_text_view)
    TextView loadingText;
    private String SAMPLE_JSON_URL = "https://jsonplaceholder.typicode.com/photos";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Making a json array request
        SingletonInstance.getInstance(this).addToRequestQueue(SimpleStringRequest.getInstance().initiateRequest(this, this, SAMPLE_JSON_URL));
    }

    @Override
    public void stringResponse(String response) {
        LogUtils.displayLogInfo("Json Array Response:::::"+response);

        loadingText.setVisibility(View.GONE);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<ModelDTO>>(){}.getType();
        List<ModelDTO> dataList = gson.fromJson(response, listType);

//        for (ModelDTO dto: dataList) {
//            LogUtils.displayLogInfo("ID:::::"+dto.getId());
//            LogUtils.displayLogInfo("Title:::::"+dto.getTitle());
//            LogUtils.displayLogInfo("====================");
//        }

//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ModelAdapter(dataList, this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        ((ModelAdapter)adapter).setOnItemClickListener(this);
    }

    @Override
    public void errorResponse(VolleyError error) {
        LogUtils.displayLogInfo("Error:::::"+error);

        loadingText.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position, View view) {
        LogUtils.displayLogInfo("Clicked on "+ position +" item");
    }
}
