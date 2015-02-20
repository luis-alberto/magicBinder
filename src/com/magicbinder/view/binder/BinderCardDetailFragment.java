package com.magicbinder.view.binder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.toolbox.NetworkImageView;
import com.magicbinder.MagicBinderApplication;
import com.magicbinder.R;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Quality;
import com.magicbinder.harmony.view.HarmonyFragment;

/**
 * BinderCardDetailFragment fragment of card detail.
 * @author Luis
 *
 */
public class BinderCardDetailFragment extends HarmonyFragment{

	/**
	 * Question for confirmation.
	 */
	private static final String QUESTION = "Do you really want to remove card from binder?";
	/**
	 * Remove a card.
	 */
	private static final String REMOVE = "Remove from binder";
	/**
	 * Binder_card.
	 */
	private Binder_Card binderCard;
	/**
	 * Card.
	 */
	private Card card;
	/**
	 * Image card.
	 */
	private NetworkImageView iconView;
	/**
	 * Button remove.
	 */
	private Button buttonRemove;
	/**
	 * Spinner Quantity.
	 */
	private Spinner spinnerQuantity;
	/**
	 * Spinner Quality
	 */
	private Spinner spinnerQuality;
	/**
	 * Button update.
	 */
	private Button buttonUpdate;
	/**
	 * View of fragment.
	 */
	private View view;

	/**
	 * Constructor of BinderCardDetailFragment.
	 * @param binderCard
	 */
	public BinderCardDetailFragment(Binder_Card binderCard){
		this.binderCard = binderCard;
	}
	/**
	 * Creating view.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(
				R.layout.fragment_bindercard_detail, container, false);
		//getting card information.
		int idCard = this.binderCard.getCard().getId();
		CardSQLiteAdapter cardSqlAdapter = new CardSQLiteAdapter(getActivity());
		cardSqlAdapter.open();
		this.card = cardSqlAdapter.getByID(idCard);
		cardSqlAdapter.close();
		//contruction of components.
		buildImageCard();
		buildRemoveButton();
		buildQualitySpinner();
		buildQuantitySpinner();
		buildUpdateButton();
		return view;
	}
	/**
	 * Create image card.
	 */
	private void buildImageCard() {
		iconView = (NetworkImageView) view.findViewById(R.id.imageCard);
		iconView.setDefaultImageResId(R.drawable.cardback);
		iconView.setImageUrl(card.getImage(), MagicBinderApplication.getInstance().getImageLoader());
	}

	/**
	 * Create and bind Quality Spinner.
	 */
	private void buildQualitySpinner() {
		//get all quality from bdd.
		QualitySQLiteAdapter quelitySqlAdapter = 
				new QualitySQLiteAdapter(getActivity());
		quelitySqlAdapter.open();
		ArrayList<Quality> qualityList = quelitySqlAdapter.getAll();
		Quality quality = quelitySqlAdapter.getByID(binderCard.getQuality().getId());
		quelitySqlAdapter.close();
		//init and bind binder spinner
		spinnerQuality = (Spinner)view.findViewById(R.id.quality);
		ArrayAdapter<Quality> dataAdapter = new ArrayAdapter<Quality>(getActivity(),
				android.R.layout.simple_spinner_item, qualityList);
		int position = -1;
		int positionTemp = 0;
		for (Quality qualityTemp : qualityList) {
			if(qualityTemp.equalsQuality(quality)){
				position = positionTemp;
			}
			positionTemp++;
		}
		spinnerQuality.setAdapter(dataAdapter);
		spinnerQuality.setSelection(position);
	}

	/**
	 * Create and bind Quality Spinner.
	 */
	private void buildQuantitySpinner() {
		//init and bind quality spinner
		spinnerQuantity = (Spinner)view.findViewById(R.id.quantity);
		ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.quantity_items, android.R.layout.simple_spinner_item);
		List<String> listString = Arrays.asList(getResources().getStringArray(R.array.quantity_items));
		String positionString = String.valueOf(binderCard.getQuantity());
		int position = listString.indexOf(positionString);
		spinnerQuantity.setAdapter(dataAdapter);
		spinnerQuantity.setSelection(position);
	}

	/**
	 * Create and bind Remove button.
	 */
	private void buildRemoveButton() {
		//init and bind quality spinner
		buttonRemove = (Button)view.findViewById(R.id.remove);
		buttonRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(getActivity())
				.setTitle(REMOVE)
				.setMessage(QUESTION)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Binder_CardSQLiteAdapter binderCardSqlAdapter= new Binder_CardSQLiteAdapter(getActivity());
						binderCardSqlAdapter.open();
						binderCardSqlAdapter.delete(binderCard);
						binderCardSqlAdapter.close();
						getActivity().finish();
					}})
					.setNegativeButton(android.R.string.no, null).show();
			}
		});
	}

	/**
	 * Create and bind update button.
	 */
	private void buildUpdateButton() {
		//init and bind quality spinner
		buttonUpdate = (Button)view.findViewById(R.id.update);
		buttonUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Binder_CardSQLiteAdapter binderCardSqlAdapter= new Binder_CardSQLiteAdapter(getActivity());
				binderCard.setQuality((Quality) spinnerQuality.getSelectedItem());
				binderCard.setQuantity(Integer.parseInt(spinnerQuantity.getSelectedItem().toString()));
				binderCardSqlAdapter.open();
				binderCardSqlAdapter.update(binderCard);
				binderCardSqlAdapter.close();
				getActivity().finish();
			}
		});
	}
}