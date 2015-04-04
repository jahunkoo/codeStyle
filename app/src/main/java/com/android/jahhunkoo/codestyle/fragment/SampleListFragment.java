package com.android.jahhunkoo.codestyle.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.jahhunkoo.codestyle.R;
import com.android.jahhunkoo.codestyle.adapter.SimpleArrayAdapter;
import com.android.jahhunkoo.codestyle.connect.AsyncCallback;
import com.android.jahhunkoo.codestyle.connect.FetchDataTask;
import com.android.jahhunkoo.codestyle.constants.BasicConstant;
import com.android.jahhunkoo.codestyle.dto.BigRegionDTO;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

/**
 * Created by Jahun Koo on 2015-04-04.
 */
public class SampleListFragment extends Fragment{

    public static final String TAG = SampleListFragment.class.getSimpleName();

    private ListView mListView;
    private SimpleArrayAdapter mSimpleArrayAdapter;

    public static SampleListFragment getInstance(FragmentManager fm){
        SampleListFragment fragment = (SampleListFragment) fm.findFragmentByTag(TAG);
        if(fragment == null){
            fragment = new SampleListFragment();
            fragment.setRetainInstance(true);
            fragment.setHasOptionsMenu(true);
        }
        return fragment;
    }

    /**
     * 데이터 초기화
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //어댑터 초기화
        mSimpleArrayAdapter = new SimpleArrayAdapter(
                getActivity(),
                new ArrayList<BigRegionDTO>());

        //데이터 다운로드
        new FetchDataTask(getActivity(),
                BasicConstant.URL_MAIN_SERVER,
                BasicConstant.URL_METHOD_GET_ALL_LIST_FOR_GSON,
                null,
                mCallback).execute();
    }

    //웹서버와 통신 후 호출되는 콜백 인터페이스
    private AsyncCallback mCallback = new AsyncCallback() {
        @Override
        public void onResult(Object result) {
            ArrayList<BigRegionDTO> bigRegionList = (ArrayList<BigRegionDTO>) result;

            mSimpleArrayAdapter.clear();
            for(BigRegionDTO bigRegion : bigRegionList){
                mSimpleArrayAdapter.add(bigRegion);
            }
            mSimpleArrayAdapter.notifyDataSetChanged();

        }

        @Override
        public void exceptionOccured(Exception e) {
        }

        @Override
        public void cancelled() {
        }
    };

    /**
     * 뷰 초기화
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_main);
        return rootView;
    }

    /**
     * bind Data & Event ... anything else
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setAdapter(mSimpleArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_important) {
            Toast.makeText(getActivity(),getResources().getString(R.string.action_important), Toast.LENGTH_LONG).show();
            return true;
        }else if(id == R.id.action_search){
            Toast.makeText(getActivity(),getResources().getString(R.string.action_search), Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
