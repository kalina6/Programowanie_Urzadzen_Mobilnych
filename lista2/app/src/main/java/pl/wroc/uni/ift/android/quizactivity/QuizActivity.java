package pl.wroc.uni.ift.android.quizactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;


    private TextView mQuestionTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int mCurrentIndex = 0;
    private int iloscpoprawnych = 0;
    private int iloscodp = 0;

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                                               updateQuestion();
                                           }
                                       });


        updateQuestion();
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        if(mQuestionsBank[mCurrentIndex].czyodp()==false){
            boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

            int toastMessageId = 0;

            if (userPressedTrue == answerIsTrue) {
                toastMessageId = R.string.correct_toast;
                iloscpoprawnych+=1;
            } else {
                toastMessageId = R.string.incorrect_toast;
            }
            mQuestionsBank[mCurrentIndex].zmienodp();
            iloscodp += 1;
            Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP| Gravity.CENTER_VERTICAL, 0 , 0);
            toast.show();
        }
        if(iloscodp == 4){
            String napis = "Koniec! Poprawnych odpowiedzi: ";
            napis = napis + iloscpoprawnych;

            Toast.makeText(this, napis, Toast.LENGTH_LONG).show();

        }
    }


}