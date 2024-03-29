package com.example.classmanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class time_table_user extends Fragment {
    private Spinner daySpinner;
    private ProgressBar mProgressCircle;

    private Context context;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    //private DocumentReference noteRef = fstore.document("Time_table/seven");

    private TimetableView timetable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_time_table_user,container,false);
        daySpinner = v.findViewById(R.id.day_spinner);
        mProgressCircle = v.findViewById(R.id.progress_circle);

        this.context = getActivity();
        timetable = v.findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Eight"))
                {
                    loadSavedData("Eight");
                }
                if(selectedItem.equals("Nine"))
                {
                    loadSavedData("Nine");
                }
                if(selectedItem.equals("Ten"))
                {
                    loadSavedData("Ten");
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        return v;
    }



        /** get  data from Firestore and then restore it in the timetable */
        private void loadSavedData(String s) {
            timetable.removeAll();
            String text =daySpinner.getSelectedItem().toString();

            ArrayList<Schedule> schedules = new ArrayList<Schedule>();
            for (int i = 0; i < 200; i++) {
                DocumentReference documentReference = fstore.collection("Time_table").document("class").collection(s).document(String.valueOf(i));
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String classroomno = documentSnapshot.getString("classroomno");
                                    String day = documentSnapshot.getString("day");
                                    int dayint = Integer.parseInt(day);
                                    String lectureend = documentSnapshot.getString("lectureend");
                                    String lecturestart = documentSnapshot.getString("lecturestart");
                                    String professor = documentSnapshot.getString("professor");
                                    String subject = documentSnapshot.getString("subject");
                                    int startindex = lecturestart.indexOf(":");
                                    int endindex = lectureend.indexOf(":");

                                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                                    Schedule schedule = new Schedule();
                                    schedule.setClassTitle(subject);
                                    schedule.setDay(dayint);
                                    schedule.setClassPlace(classroomno); // sets place
                                    schedule.setProfessorName(professor); // sets professor
                                    schedule.setStartTime(new Time(Integer.valueOf(lecturestart.substring(0, startindex)), lecturestart.lastIndexOf("0" + 1))); // sets the beginning of class time (hour,minute)
                                    schedule.setEndTime(new Time(Integer.valueOf(lectureend.substring(0, endindex)), lectureend.lastIndexOf("0" + 1))); // sets the end of class time (hour,minute)
                                    schedules.add(schedule);
//.. add one or more schedules
                                    timetable.add(schedules);

                                    mProgressCircle.setVisibility(View.INVISIBLE);

                                } else {
                                    // Toast.makeText(time_table.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, e.toString());
                                mProgressCircle.setVisibility(View.INVISIBLE);

                            }
                        });


                timetable.add(schedules);
            }
        }
    }
