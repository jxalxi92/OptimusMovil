package pe.com.cmacica.optimusmovil.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.cmacica.optimusmovil.R;

/**
 * Fragmento para el contenido principal
 */
public class PlaceHolderFragment extends Fragment {

    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_SECTION_TITLE = "section_number";


    public PlaceHolderFragment() {
        // Required empty public constructor
    }

    /**
     * Crea una instancia prefabricada de {@link PlaceHolderFragment}
     *
     * @param sectionTitle Título usado en el contenido
     * @return Instancia dle fragmento
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceHolderFragment newInstance(String sectionTitle) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment, container, false);

        // Ubicar argumento en el text view de section_fragment.xml
        String title = getArguments().getString(ARG_SECTION_TITLE);
        TextView titulo = (TextView) view.findViewById(R.id.title);
        titulo.setText(title);
        return view;
    }



}
