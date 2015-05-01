package com.penpaperrpg.penandpaperrpg.app.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.penpaperrpg.penandpaperrpg.R;
import com.penpaperrpg.penandpaperrpg.app.adapters.ChatListAdapter;
import com.penpaperrpg.penandpaperrpg.model.dao.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = RoomFragment.class.getName();
    private static String sUserId;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatListAdapter mAdapter;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static final int CHAT_REFRESH_RATE = 1000;
    // Create a handler which can run code periodically
    private Handler handler = new Handler();

    // region dice

    Button btDie2;
    Button btDie4;
    Button btDie6;
    Button btDie8;
    Button btDie10;
    Button btDie12;
    Button btDie20;
    Button btDie100;

    // endregion dice

    public static final String USER_ID_KEY = "userId";

    private EditText etMessage;
    private Button btSend;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_room2, container, false);

        btDie2 = (Button)rootView.findViewById(R.id.btDie2);
        btDie2.setOnClickListener(this);
        btDie4 = (Button)rootView.findViewById(R.id.btDie4);
        btDie4.setOnClickListener(this);
        btDie6 = (Button)rootView.findViewById(R.id.btDie6);
        btDie6.setOnClickListener(this);
        btDie8 = (Button)rootView.findViewById(R.id.btDie8);
        btDie8.setOnClickListener(this);
        btDie10 = (Button)rootView.findViewById(R.id.btDie10);
        btDie10.setOnClickListener(this);
        btDie12 = (Button)rootView.findViewById(R.id.btDie12);
        btDie12.setOnClickListener(this);
        btDie20 = (Button)rootView.findViewById(R.id.btDie20);
        btDie20.setOnClickListener(this);
        btDie100 = (Button)rootView.findViewById(R.id.btDie100);
        btDie100.setOnClickListener(this);

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser(rootView);
        } else { // If not logged in, login as a new anonymous user
            login(rootView);
        }

        // Run the runnable object defined every 100ms
        handler.postDelayed(runnable, CHAT_REFRESH_RATE);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    // region Parse

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login(final View rootView) {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser(rootView);
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser(View rootView) {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting(rootView);
    }

    // Setup message field and posting
    private void setupMessagePosting(View rootView) {
        etMessage = (EditText) rootView.findViewById(R.id.etMessage);
        btSend = (Button) rootView.findViewById(R.id.btSend);
        lvChat = (ListView) rootView.findViewById(R.id.lvChat);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatListAdapter(getActivity(), sUserId, mMessages);
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessageToParse(etMessage.getText().toString());
            }
        });
    }

    private void sendMessageToParse(String text) {
        String body = text;
        // Use Message model to create new messages now
        Message message = new Message();
        message.setUserId(sUserId);
        message.setBody(body);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                receiveMessage();
            }
        });
        etMessage.setText("");
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    lvChat.invalidate(); // redraw listview
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, CHAT_REFRESH_RATE);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }

    // endregion Parse

    // region dice

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btDie2:
                sendMessageToParse(rollDice(2));
                break;
            case R.id.btDie4:
                sendMessageToParse(rollDice(4));
                break;
            case R.id.btDie6:
                sendMessageToParse(rollDice(6));
                break;
            case R.id.btDie8:
                sendMessageToParse(rollDice(8));
                break;
            case R.id.btDie10:
                sendMessageToParse(rollDice(10));
                break;
            case R.id.btDie12:
                sendMessageToParse(rollDice(12));
                break;
            case R.id.btDie20:
                sendMessageToParse(rollDice(20));
                break;
            case R.id.btDie100:
                sendMessageToParse(rollDice(100));
                break;
            default:
                break;
        }
    }

    // endregion dice

    private String rollDice(int dieNum){
        Random randomGenerator = new Random();

        int randomInt = randomGenerator.nextInt(dieNum);
        return String.valueOf(randomInt + 1);
    }



}
