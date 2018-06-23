package com.example.yuangongguanli.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yuangongguanli.Entity.Employee;
import com.example.yuangongguanli.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    List<Employee> employees = new ArrayList<>();
    Context context;
    public EmployeeAdapter(Context context){
        this.context=context;
    }
    public void addEmployees(List<Employee> emps){
        if(emps!=null){
            employees.addAll(emps);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Employee getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder = new ViewHolder();

            convertView = convertView.inflate(context, R.layout.listview_item,null);
            viewHolder.textView_Id=convertView.findViewById(R.id.textView_Employee_Id);
            viewHolder.textView_Name = convertView.findViewById(R.id.textView_Employee_Name);
            viewHolder.textView_Salary = convertView.findViewById(R.id.textView_Employee_Salary);
            viewHolder.textView_Age = convertView.findViewById(R.id.textView_Employee_Age);
            viewHolder.textView_Gender = convertView.findViewById(R.id.textView_Employee_Gender);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Employee employee = getItem(position);
        viewHolder.textView_Name.setText(employee.getName());
        viewHolder.textView_Id.setText(String.valueOf(employee.getId()));
        viewHolder.textView_Salary.setText(String.valueOf(employee.getSalary()));
        viewHolder.textView_Age.setText(String.valueOf(employee.getAge()));
        String gender="";
        if(employee.getGender().equals("m")){
            gender="男";
        }else{
            gender="女";
        }
        viewHolder.textView_Gender.setText(gender);

        return convertView;
    }
    private class ViewHolder{
        TextView textView_Id;
        TextView textView_Name;
        TextView textView_Age;
        TextView textView_Gender;
        TextView textView_Salary;
    }
}
