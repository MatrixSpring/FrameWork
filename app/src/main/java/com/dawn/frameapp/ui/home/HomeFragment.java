package com.dawn.frameapp.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dawn.frameapp.R;
import com.dawn.frameapp.bean.Student;
import com.dawn.libframe.permission.PermissionListener;
import com.dawn.libframe.permission.PermissionUtil;
import com.dawn.libframe.sqlcipher.DaoSupportFactory;
import com.dawn.libframe.sqlcipher.IDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"fdfdfdfdfd",Toast.LENGTH_LONG).show();
//                testDB();
                testDBUtils();
            }
        });
        return root;
    }

    private void testDB(){
        PermissionUtil permissionUtil = new PermissionUtil(HomeFragment.this);
        permissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                testDBUtils();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }

            @Override
            public void onShouldShowRationale(List<String> deniedPermission) {

            }
        });
    }

    public void testDBUtils(){
        IDaoSupport<Student> dao = DaoSupportFactory.getInstance(getActivity()).getDao(Student.class);
        List<Student> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(new Student("在线", 18 + i));
        }
        dao.insert(persons);
        List<Student> list = dao.querySupport().queryAll();
        Log.d("testDBUtils","testDBUtils "+list.size());
        Toast.makeText(getActivity(), list.size() + "", Toast.LENGTH_SHORT).show();
    }
}