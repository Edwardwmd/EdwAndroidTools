# 前言：



&emsp;&emsp;关于Android的屏幕适配我想大家都不陌生了，Android 的设备多种多样，屏幕碎片化严重，有着不同的屏幕尺寸和像素密度。 尽管系统可通过基本的缩放和调整大小功能使界面适应不同屏幕，但还需要进一步优化，以确保界面能够在各类屏幕上趋近完美的展现。

&emsp;&emsp;屏幕适配这个问题其实在开放过程中给人呈现出来的结果是矛盾的，你可以说它是个小问题，也可说它是你开发之路给你使绊子的问题，无论是Android也好，IOS也罢，大家或多或少会遇到相类似问题，正是因为这样的问题，亟待需要我们去解决和优化，所以，本人抱着学习的心态探讨关于Android屏幕适配方案这个问题，首先要说明一点的是，没有任何完美的解决方案，你只能在原有的方案对其进行优化，令它趋近于完美。

#  实现：

&emsp;&emsp;关于这个屏幕适配方案其实也是之前传的沸沸扬扬的今日头条解决方案，在此先感谢今日头条团队贡献出来的优秀方案。此前，也见证了不同的屏幕适配方案的崛起：

+ `简单的有Linearlayout的weight，但此方案中的所有布局只能依赖Linearlayout，一旦布局复杂，性能会变得非常差；`
+ `google官方推荐的ConstraintLayout方案，是针对Linearlayout使用weight导致性能差的而提出解决方案，ConstraintLayout的百分比适配也做的不错，适配时性能优于Linearlayout，但不是最优的解决方案。`
+ `鸿洋大神的百分比方案，此方案就是在项目中针对你所需要适配的手机屏幕的分辨率各自简历一个文件夹，这个方案虽然可以较好的适配屏幕，但一旦需要适配的手机屏幕分辨率多的话，所需要res中的values文件也变多，使得App臃肿，适配繁琐；`
+ `鸿洋大神的AndroidAutoLayout，此方案是建立在百分比适配方案上进一步改良和优化的，这个方案支持自定义控件适配拓展，很强大的一个库，但入侵行较大，使用时需要继承AutoLayoutActivity，或者选择性的继承AutoLinearLayout、AutoRelativeLayout、AutoFrameLayout等实现适配工作，另外此方案不在维护了。`

在进行最优方案屏幕适配前，我们来讲讲关于屏幕的相关基础知识，在Android中引用了dp作为布局控件的适配单位，android中的dp在渲染前会将dp转为px，其计算公式为：

```kotlin
  density = dpi/160

  px = dp*density
  
  px = dp*(dpi/160)

```

&emsp;&emsp;而dpi是根据屏幕真实的分辨率和尺寸来计算的，每个设备都可能不一样的。通常情况下，一部手机的分辨率是宽x高，屏幕大小是以寸为单位，那么三者的关系是：

![像素](https://gitee.com/Edwardwmd/image-resource/raw/93af85628e9d0d82d4333356fcfa82e32c55b88b/%E5%B1%8F%E5%B9%95%E9%80%82%E9%85%8D-1.png)

&emsp;&emsp;从dp和px的转换公式 ：px = dp * density
可以看出，如果设计图宽为360dp，想要保证在所有设备计算得出的px值都正好是屏幕宽度的话，我们只能修改 density 的值。
通过阅读源码，我们可以得知，density 是 DisplayMetrics 中的成员变量，而 DisplayMetrics 实例通过 Resources.getDisplayMetrics 可以获得，而Resouces通过Activity或者Application的Context获得。

```java
DisplayMetrics {
   ...
       
    public int widthPixels;
   
    public int heightPixels;
  
    public float density;
 
    public int densityDpi;
   
    public float scaledDensity;
  
    public float xdpi;
  
    public float ydpi;
    
    ...
}
```

先来熟悉下 DisplayMetrics 中和适配相关的几个变量：

+ `DisplayMetrics.density 就是上述的density`
+ `DisplayMetrics.densityDpi 就是上述的dpi`
+ `DisplayMetrics.scaledDensity 字体的缩放因子，正常情况下和density相等，但是调节系统字体大小后会改变这个值`

前面我们讲到，无论你的布局控件用什么单位，最终都会转换成px,这也是一个重要的点，在源码中TypedValue类可知其结果：


```java
public static float applyDimension(int unit, float value,DisplayMetrics metrics){
        switch (unit) {
        case COMPLEX_UNIT_PX:
            return value;
        case COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f/72);
        case COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f/25.4f);
        }
        return 0;
    }
```



原理后面补上，先鸽一阵。。。。。。。。

# 效果

&emsp;&emsp;自己按照今日头条团队给出的核心代码写了个小框架，目前未作过多扩展，实现的效果很不错，明确的说上手简单，成本低，入侵性低，不影响整体布局性能，实现的效果图如下：

![](https://gitee.com/Edwardwmd/image-resource/raw/a90cb546e709b8538720383664a6bd6cc0cb2436/phone.png)