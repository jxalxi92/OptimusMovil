package pe.com.cmacica.optimusmovil.View;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.cmacica.optimusmovil.R;
import pe.com.cmacica.optimusmovil.provider.ContractParaProducto;
import pe.com.cmacica.optimusmovil.sync.SyncAdapter;
import pe.com.cmacica.optimusmovil.utils.Utilidades;


public class FacturaFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorDeProducto adapter;


    private TextView emptyView;

    public static final String ARG_SECTION_TITLE = "section_number";
    public FacturaFragment() {
        // Required empty public constructor
    }

    public static FacturaFragment newInstance(String sectionTitle) {

        FacturaFragment fragment = new FacturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toast.makeText(getContext(),
                "onCreateView",
                Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_factura, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorDeProducto(getContext());
        recyclerView.setAdapter(adapter);
        emptyView =(TextView) view.findViewById(R.id.recyclerview_data_empty);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "intent inicio",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), InsertActivity.class);
                Toast.makeText(getContext(),
                        "intent fin",
                        Toast.LENGTH_LONG).show();
                if (Utilidades.materialDesign())
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

                else startActivity(intent);

            }
        });

      //  getActivity().getSupportLoaderManager().initLoader(0, null, this);

        SyncAdapter.inicializarSyncAdapter(getContext());

        return view;

    }
    public void onClickFab(View v) {
        Intent intent = new Intent(getActivity(), InsertActivity.class);
        if (Utilidades.materialDesign())
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        else startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Toast.makeText(getContext(),
                "onCreateLoader",
                Toast.LENGTH_LONG).show();
        emptyView.setText("Cargando datos...");
        // Consultar todos los registros
        return new CursorLoader(
                getContext(),
                ContractParaProducto.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getContext(),
                "onLoadFinished",
                Toast.LENGTH_LONG).show();
        adapter.swapCursor(data);
        emptyView.setText("");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Toast.makeText(getContext(),
                "onLoaderReset",
                Toast.LENGTH_LONG).show();
        adapter.swapCursor(null);
    }
}
