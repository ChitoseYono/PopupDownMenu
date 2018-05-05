# PopupDownMenu
PopupDownMenu = PopupWindow+ListView

## 1、PopupDownMenu介绍
想必大家在各种诸如美团，淘宝等app上都有看到过这样的下拉菜单筛选栏，而据我所知，别人还原的做法大多是用单纯的ListView或者ExpandableListView更或者自定义View之类的来实现，但我是第一想法使用利用PopupWindow+ListView的方法实现的，所以就有了这个项目。
同样，为了提高这个的利用性，我为PopupWindow设置了一个包装类，判断不同级别的目录有不同的做法，因为一般的app也只会做到三级目录，我在操作上就只做到三级的效果了。但是有了这个包装类，实现起来将会十分简单~只需要按规则放入数据就可以了。（因为是实现效果了就放出来了，就求不要吐槽界面丑了）

## 2、直接看预览图






## 3、使用方法


### 3.1Gradle
在project 中build.gradle下增加:

    allprojects {  
      repositories {  
          maven { url 'https://jitpack.io' }  
      }
     }
  
在app中build.gradle下添加：

    dependencies {  
      implementation 'com.github.ChitoseYono:PopupDownMenu:v1.0.2'
      }
  
### 3.2布局文件
以单单一个三级菜单为例，需要一个有三个ListView的布局文件：

  <?xml version="1.0" encoding="utf-8"?>  
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
      android:layout_width="match_parent"  
      android:layout_height="match_parent"  
      android:orientation="horizontal"  
      android:baselineAligned="false"  
      >  

      <ListView  
          android:id="@+id/pop_listview_left"  
          android:layout_width="match_parent"  
          android:layout_height="wrap_content"  
          android:layout_weight="1"  
          android:dividerHeight="0.5dp"  
          android:divider="#e2e2e2"  
          android:scrollbars="none"  
          android:scrollingCache="false"  
          />  

      <ListView  
          android:id="@+id/pop_listview_center"  
          android:layout_width="match_parent"  
          android:layout_height="wrap_content"  
          android:scrollbars="none"  
          android:layout_weight="1"  
          android:background="#efefef"  
          android:dividerHeight="0.5dp"  
          android:divider="#c6c6c6"  
          android:scrollingCache="false"/>  

      <ListView  
          android:id="@+id/pop_listview_right"  
          android:layout_width="match_parent"  
          android:layout_height="wrap_content"  
          android:scrollbars="none"  
          android:layout_weight="1"  
          android:background="#e2e2e2"  
          android:dividerHeight="0.5dp"  
          android:divider="#c6c6c6"  
          android:scrollingCache="false"/>  

  </LinearLayout>  
  
（在这里你可以自定义如divider,scrollbars,background,dividerHeight等属性[备注：这里setListSelector可能会显示有问题，不建议加]）

### 3.3数据

    itemList.add(new PopItem(1, 0, "语言"));  
    itemList.add(new PopItem(2, 1, "说话的"));  
    itemList.add(new PopItem(3, 1, "编程的"));  
    itemList.add(new PopItem(4, 1, "凑数的"));  
    itemList.add(new PopItem(5, 1, "还是凑数的"));  
    itemList.add(new PopItem(6, 2, "中文"));  
    itemList.add(new PopItem(7, 2, "英文"));  
    itemList.add(new PopItem(8, 2, "法语"));  
    itemList.add(new PopItem(9, 2, "日语"));  
    itemList.add(new PopItem(10, 3, "C语言"));  
    itemList.add(new PopItem(11, 3, "Java"));  
    itemList.add(new PopItem(12, 3, "Python"));  
    itemList.add(new PopItem(13, 0, "性别"));  
    itemList.add(new PopItem(14, 13, "男性"));  
    itemList.add(new PopItem(15, 13, "女性"));  
    itemList.add(new PopItem(16, 0, "课程"));  
    itemList.add(new PopItem(17, 16, "公选课"));  
    itemList.add(new PopItem(18, 16, "专业课"));  
    itemList.add(new PopItem(19, 17, "大英"));  
    itemList.add(new PopItem(20, 17, "数学"));  
    itemList.add(new PopItem(21, 18, "数据库"));  
    itemList.add(new PopItem(22, 18, "数据结构"));  
    itemList.add(new PopItem(23, 18, "操作系统"));  
    itemList.add(new PopItem(24, 0, "凑数"));
  
数据的形式十分简单：id就是这个item的id,pid就是他的父类id,名称就不多说了。（切记要按照id顺序放入，不然会报错）

### 3.4PopupDownMenu的初始化

    //初始化PopupWindow中的三个ListView  
    view = LayoutInflater.from(this).inflate(R.layout.popup_triple_layout, null);  
    firstListView = view.findViewById(R.id.pop_listview_left);  
    secondListView = view.findViewById(R.id.pop_listview_center);  
    thirdListView = view.findViewById(R.id.pop_listview_right);  

    p = new PopupDownMenu(this, itemList, width, height, view, drawable, firstListView, secondListView, thirdListView);  
    p.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {  
        @Override  
        public void onDismiss() {  
           //处理数据方式要防止取得的数据是不正常的数据  
           if (p.results[3] != null) {  
                  Toast.makeText(MainActivity.this, p.results[0] + "+" + p.results[1] + "+" + p.results[2], Toast.LENGTH_SHORT).show();  
            }
        }
     });
  
三级菜单就需要三个ListView，然后定义你需要的PopupDownMenu的宽和高，还要自定义一个drawable作为PopupDownMenu的背景。
而数据的获得就需要通过PopupDownMenu中包装的popupWindow的OnDismiss中回调获得(备注：p.result[3]是数据正常输出标记，没有这个判定可能会导致数据缓存导致的异常而得到不理想的数据)



### 3.5给事件源增添加监听事件

    tv_demo.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View v) {  
    //判断是否popupWindow是否已经在显示，没显示就显示，显示就dismiss  
        if (!p.popupWindow.isShowing()) {  
            p.popupWindow.showAsDropDown(v,0,0);  
        }  
        else {  
            p.popupWindow.dismiss();  
        }  
    }
    });  
  
这就能实现显示了点击按钮时：没显示就显示，显示了就收回了，就此基本上完成了正常的功能的实现了！
不过要实现图中的效果还需要加上一些东西的...例如点击外部popWindow的消失，popWindow显示时的变暗效果，多个PopupDownMenu时的兼容效果等等来完善用户体验，这些也需要你们自己写在Activity里的，我在下面写吧


## 4、用户体验的完善
### 4.1实现点击外部布局的popupWindow消失

    @Override  
    public boolean onTouchEvent(MotionEvent event) {  

      switch (event.getAction()) {  
          //点击按钮以外的布局时要把可能有的PopupWindow清除  
          case MotionEvent.ACTION_UP:  
              if (p.popupWindow.isShowing()) {  
                  p.popupWindow.dismiss();  
              }  
              break;  
          default:  
              break;  
      }  
      return true;  
    }  

如果只是点击事件源的话不会触发这个，因为会被事件源的监听拦下，所以放心加上这个就可以了！

### 4.2变暗效果
这个效果的话...我就有点投机取巧了。这里需要你们的Activity里的布局有一个DarkView布局，像这样

    <View  
      android:id="@+id/main_darkview"  
      android:layout_width="match_parent"  
      android:layout_height="match_parent"  
      android:background="#9b9b9b"  
      android:visibility="gone"/>  
      
只要在事件源的onClick里面加上

`darkView.setVisibility(View.VISIBLE);  `

再在PopupDownMenu的onDismiss中加上：

`darkView.setVisibility(View.GONE);  `

很简单易懂吧~不过人性化设计一点就要加上渐变动画了，这个可以参照我的示例代码中的anim文件就行，其实就是startAnimation的事。

### 4.3多个Menu时的兼容

在实际使用时应该不会只有一个出现，所以需要解决一些多个PopupDownMenu可能会产生的问题：
实际上也很好解决的，假设还有两个，那么只要在事件源的onClick里面加上：

    //避免另外两个pop窗口还存在的情况  
    if (p1.popupWindow.isShowing()) {  
        p1.popupWindow.dismiss();  
    }  
    if (p2.popupWindow.isShowing()) {  
        p2.popupWindow.dismiss();  
    }  

### 4.4小箭头效果
首先你得准备两个小箭头图片...然后做一个像这样的selector：

    <?xml version="1.0" encoding="utf-8"?>  
    <selector xmlns:android="http://schemas.android.com/apk/res/android">  
    <item android:state_selected="true"  
        android:drawable="@drawable/ic_up"/>  
    <item android:state_selected="false"  
        android:drawable="@drawable/ic_down"/>  
    </selector>  

再在你的代码中对事件源的onClick和PopupDownWindow的onDismiss中分别加上

`tv.setSelected(true);  `和`tv.setSelected(false);  `

就ojbk了（这个我本来不太想的，因为应该都知道怎么做的吧）
总之！到此就能实现预览图的效果了！



## 5、其他事项
### 1.对于ListView里的各项的布局的自定义设置

这个需要直接改依赖包library中的东西了。

直接改`res/popup_item.xml`就可以了。

### 2.对于ListView各项点击效果的自定义设置

这个...也需要改Library中的文件了，直接改`res/item_selector`就可以了，放一张截图吧：


### 3.其他一些话

这也只是大概也就做了几天的一个小想法，像上面两点需要到依赖包里改文件这问题我会再想想怎么处理的，也可能存在没发现的Bug，或者设计很丑之类的问题，又或者设计理念上的更新，我都会注意处理的。

总之，欢迎大家评论提出问题和使用上的其他问题，我会尽快回复！过一段时间我也会试着再写一篇blog说明是怎么做的。

另外各位客官github求一波star、fork、issue三连啦。



[我的博客](https://blog.csdn.net/chitoseyono "欢迎关注ChitoseYono的博客")

