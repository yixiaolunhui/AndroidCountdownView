# AndroidCountdownView
圆形倒计时


#效果图
![image](https://github.com/dalong982242260/AndroidCountdownView/blob/master/gif/countdown.gif?raw=true)

##说明
|name|format|description|
|:---:|:---:|:---:|
| backgroundColor | color |圆形背景颜色
| roundColor | color |圆环颜色
| roundProgressColor | color |进度条颜色
| roundWidth | dimension |圆环的宽度
| text | string |控件中间的文字
| textSize | dimension |文字的大小
| textColor | color |文字的颜色
| countdownTime | integer |倒计时的时间


##使用

gradle：
 
            compile 'com.dalong:countdownview:1.0.0'
            
xml：


            <com.dalong.countdownview.CountDownView
                    android:id="@+id/countDownView"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:padding="20dp"
                    app:roundColor="@color/roundColor"
                    app:backgroundColor="@color/backgroundColor"
                    app:roundProgressColor="@color/roundProgressColor"
                    app:countdownTime="10000"
                    app:roundWidth="3dp"
                    app:textSize="18sp"
                    app:text="点击跳过"
                    app:textColor="@color/textColor"
                    />
                    
 java：
                    
                    
                  countDownView=(CountDownView)findViewById(R.id.countDownView);
                  countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
                      @Override
                      public void start() {
                          Toast.makeText(MainActivity.this, "倒计时开始", Toast.LENGTH_SHORT).show();
                      }
          
                      @Override
                      public void finish() {
                          Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                      }
                  });     
                  //开始倒计时             
                  countDownView.startCountdown();   
                  //停止倒计时 
                  countDownView.stopCountdown();
                               
##License

Copyright 2016 dalong

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.                               