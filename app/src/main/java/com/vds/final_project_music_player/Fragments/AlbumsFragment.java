package com.vds.final_project_music_player.Fragments;


import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vds.final_project_music_player.Activities.MainActivity;
import com.vds.final_project_music_player.Adapters.AlbumAdapter;
import com.vds.final_project_music_player.DataLoaders.AlbumLoader;
import com.vds.final_project_music_player.Models.AlbumInfo;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {

    private AlbumAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private PreferencesUtility preferencesUtility;
    private boolean isGrid;
    ImageLoader imageLoader;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("muAp","vds onCreate");
        super.onCreate(savedInstanceState);
        preferencesUtility = PreferencesUtility.getInstance(getActivity());
        isGrid = preferencesUtility.isAlbumsGrid();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("muAp","vds onCreateView");
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recyclerview,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerview);

        setLayoutManager();

        if (getActivity() != null)
            new loadAlbums().execute();
        return rootView;
    }

    private void setLayoutManager(){
        if (isGrid){
            layoutManager = new GridLayoutManager(getActivity(),2);

        }else {
            layoutManager = new GridLayoutManager(getActivity(),1);
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemDecoration(){
        if (isGrid){
            int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen.spacing_card_album_grid);
            itemDecoration = new SpacesItemDecoration(spacingInPixels);
        }else {
            itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        }
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void updateLayoutManager(int column){
        recyclerView.removeItemDecoration(itemDecoration);
        recyclerView.setAdapter(new AlbumAdapter(getActivity(), AlbumLoader.getAllAlbums(getActivity())));
        layoutManager.setSpanCount(column);
        layoutManager.requestLayout();
        setItemDecoration();
    }

    private void reloadAdapter(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                List<AlbumInfo> albumList = AlbumLoader.getAllAlbums(getActivity());
                adapter.updateDataSet(albumList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("muAp","vds onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

        public SpacesItemDecoration(int space){
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
            outRect.left = space;
            outRect.right = space;
            outRect.top = space;
            outRect.bottom = space;
        }
    }

    public class loadAlbums extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            if(getActivity() != null){
                Log.d("muAp","vds load albums");
                adapter = new AlbumAdapter(getActivity(),AlbumLoader.getAllAlbums(getActivity()));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            recyclerView.setAdapter(adapter);
            if(getActivity() != null){
                setItemDecoration();
            }
        }
    }
}
