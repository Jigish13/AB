package com.example.jiggy.ab.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiggy.ab.APIManager;
import com.example.jiggy.ab.Adapter.DoubtAnswerAdapter;
import com.example.jiggy.ab.Constant;
import com.example.jiggy.ab.R;
import com.example.jiggy.ab.model.Answer;
import com.example.jiggy.ab.model.Doubt;
import com.example.jiggy.ab.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoubtsQuestionActivity extends AppCompatActivity {
    ArrayList<Answer> answerList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    TextView no_of_upvotes, doubt_question, doubt_question_tag, doubt_question_detailed_form_text, no_of_answers;
    RecyclerView rv_doubt_question_answers;
    NestedScrollView sv_doubts_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts_question);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent ii = getIntent();
        Doubt doubt_object = (Doubt) ii.getParcelableExtra("doubt_object");
        String total_upvotes = "" + doubt_object.getUpVote();
        String doubt_quest = "" + doubt_object.getDoubtHeading();
        String doubtTag = doubt_object.getTag();

        int doubtID = doubt_object.getDoubtId();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)//base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIManager api = retrofit.create(APIManager.class);
        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getAnswer(doubtID, 0, 10);
        final ProgressDialog progressDialog = new ProgressDialog(DoubtsQuestionActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        no_of_upvotes = (TextView) findViewById(R.id.no_of_upvotes);
        doubt_question = (TextView) findViewById(R.id.doubt_question);
        doubt_question_tag = (TextView) findViewById(R.id.doubt_question_tag);
        doubt_question_detailed_form_text = (TextView) findViewById(R.id.doubt_question_detailed_form_text);
        no_of_answers = (TextView) findViewById(R.id.no_of_answers);
        sv_doubts_question = (NestedScrollView) findViewById(R.id.sv_doubts_question);

        rv_doubt_question_answers = (RecyclerView) findViewById(R.id.rv_doubt_question_answers);

        /*sv_doubts_question.fullScroll(ScrollView.FOCUS_UP);*/
        sv_doubts_question.smoothScrollTo(0, 0);

        /*String doubt_quest_tag = "";
        for (int i = 0, n = doubt_quest_tag_list.size(); i < n; i++) {
            doubt_quest_tag += doubt_quest_tag_list.get(i);
        }*/

        no_of_upvotes.setText(total_upvotes);
        doubt_question.setText(doubt_quest);
        doubt_question_tag.setText(doubtTag);

        doubt_question_detailed_form_text.setText(doubt_object.getDoubt());
      /*  User user1 = new User(1, "xx@xx.com", "abc@gmail.com", "Abhi", "Koranne", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user2 = new User(2, "abc@gmail.com", "abc@gmail.com", "JIGGY", "VYAS", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user3 = new User(3, "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8", "abc@gmail.com", "Shabbi", "SRK", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user4 = new User(4, "ac@gmail.com", "abc@gmail.com", "GABRU", "JAVA", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");
        User user5 = new User(5, "bc@gmail.com", "abc@gmail.com", "NABDU", "Dot NET", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.internetvibes.net%2Fwp-content%2Fgallery%2Favatars%2F017.png&imgrefurl=https%3A%2F%2Fwww.internetvibes.net%2Fgallery%2Fnice-avatar-set-613-avatars-100x100%2F&docid=TOdPgfD5Tee_eM&tbnid=7fp-HioZO06DsM%3A&vet=10ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw..i&w=100&h=100&bih=653&biw=1517&q=images%20100x100&ved=0ahUKEwjRq9i2ybPYAhWMpY8KHffNBp0QMwhFKAcwBw&iact=mrc&uact=8");

        answerList.add(new Answer(5000, 1, 00, "This is comment 1", 13, 1, "This is answer 1", user1));
        answerList.add(new Answer(100000, 2, 01, "This is comment 2", 23, 2, "This is answer 2", user2));
        answerList.add(new Answer(2149813, 3, 02, "This is comment 3", 11, 3, "This is answer 3", user3));
        answerList.add(new Answer(324712, 4, 03, "This is comment 4", 44, 4, "This is answer 4", user4));
        answerList.add(new Answer(123124, 5, 04, "This is comment 5", 16, 5, "This is answer 5", user5));


        DoubtAnswerAdapter adapt = new DoubtAnswerAdapter(this, answerList);
        rv_doubt_question_answers.setAdapter(adapt);
        rv_doubt_question_answers.setLayoutManager(new LinearLayoutManager(this));*/

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow4
                    if (response != null && response.body() != null) {
                        Toast.makeText(DoubtsQuestionActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        Log.d("errorDoubt", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> map = response.body();

                        // Convert JSONArray to your custom model class list as follow
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(map);

                        Log.d("errorDoubt", "jsonString: " + jsonString);

                        JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                        Log.d("errorDoubt", "content: " + content);

                        JsonArray userInfoArr = content.getAsJsonArray("UserInfo");
                        JsonArray answerListArr = content.getAsJsonArray("answerList");
                        Log.d("errorDoubt", "contentDoubtID: " + answerListArr.get(0).getAsJsonObject().get("doubtId").getAsInt());


                        int answerId, doubtId, upVotes, downVotes, userId;
                        long timestamp;
                        String text, answerImageURL, firstName, lastName, profilePic;

                        String[] userData;

                        for (int i = 0, n = userInfoArr.size(); i < n; i++) {
                            userData = userInfoArr.get(i).getAsString().split(",");
                            userId = Integer.parseInt(userData[0]);
                            firstName = userData[1];
                            lastName = userData[2];
                            profilePic = userData[3];

                            answerId = answerListArr.get(i).getAsJsonObject().get("answerId").getAsInt();
                            doubtId = answerListArr.get(i).getAsJsonObject().get("doubtId").getAsInt();
                            upVotes = answerListArr.get(i).getAsJsonObject().get("upvote").getAsInt();
                            downVotes = answerListArr.get(i).getAsJsonObject().get("downvote").getAsInt();
                            timestamp = answerListArr.get(i).getAsJsonObject().get("createTime").getAsLong();
                            text = answerListArr.get(i).getAsJsonObject().get("text").getAsString();
                            //Log.d("ErrorText", "TEXT " + text[i]);

                            if (answerListArr.get(i).getAsJsonObject().get("answerImage") != null) {
                                answerImageURL = answerListArr.get(i).getAsJsonObject().get("answerImage").getAsString();
                            } else {
                                answerImageURL = null;
                            }
                            // Log.d("ErrorIMG", "IMG "+answerImageURL[i]);
                            usersList.add(new User(userId, profilePic, null, firstName, lastName, answerImageURL));
                            answerList.add(new Answer(timestamp, doubtId, answerId, upVotes, downVotes, text, usersList.get(i)));
                        }
                        displayAnswers();
                    } else {
                        Toast.makeText(DoubtsQuestionActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(DoubtsQuestionActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(DoubtsQuestionActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });


        no_of_answers.setText("" + doubt_object.getNumberOfAnswers() + " Answers");
    }

    private void displayAnswers() {
        DoubtAnswerAdapter adapt = new DoubtAnswerAdapter(this, answerList);
        rv_doubt_question_answers.setAdapter(adapt);
        rv_doubt_question_answers.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addAnswer(View view) {
        startActivity(new Intent(DoubtsQuestionActivity.this, AddAnswerActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
