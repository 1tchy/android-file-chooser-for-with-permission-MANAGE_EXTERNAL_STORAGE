# android-file-chooser working with MANAGE_EXTERNAL_STORAGE permission of Android 11

This is a fork of the android-file-chooser, but adapted to work with the
MANAGE_EXTERNAL_STORAGE permission from Android 11.

### android-file-chooser

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--file--chooser-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6982)
[![Release](https://jitpack.io/v/hedzr/android-file-chooser.svg)](https://jitpack.io/#hedzr/android-file-chooser)
[![Build Status](https://travis-ci.com/hedzr/android-file-chooser.svg?branch=master)](https://travis-ci.com/hedzr/android-file-chooser)

###  android-file-chooser-for-with-permission-MANAGE_EXTERNAL_STORAGE
[![Relese](https://jitpack.io/v/1tchy/android-file-chooser-for-with-permission-MANAGE_EXTERNAL_STORAGE.svg)](https://jitpack.io/#1tchy/android-file-chooser-for-with-permission-MANAGE_EXTERNAL_STORAGE/)
[![API](https://img.shields.io/badge/API-30%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=30)

`android-file-library` is a lightweight file/folder chooser.

The usages at [HERE](#Usages), and [Acknowledges](#Acknowledges).


## Overview

![banner](captures/banner.svg)


### Demo Application

A demo-app of the original can be installed from [Play Store](https://play.google.com/store/apps/details?id=com.obsez.android.lib.filechooser.demo).

<a href='https://play.google.com/store/apps/details?id=com.obsez.android.lib.filechooser.demo&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' width='240' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>

**NOTE**:

Please don't forget to check the [**_upstream_**](https://github.com/hedzr/android-file-chooser) and give it a :star:!\
You can also support him via [opencollective](https://opencollective.com/android-file-chooser) and [paypal](https://paypal.me/hezr/3).

## Usages

### Configuration

#### build.gradle

1. add the jitpack repository url to your root build.gradle:

```gradle
allprojects {
    repositories {
        google()
        maven { url "https://jitpack.io" }
    }
}
```

2. import `android-file-chooser`

```gradle
implementation 'com.github.1tchy:android-file-chooser-for-with-permission-MANAGE_EXTERNAL_STORAGE:master-SNAPSHOT'
```

> **Tips for using JitPack.io**
>
> To disable gradle local cache in your project, add stretegy into your top `build.grable`:
>
> ```gradle
> configurations.all {
>     resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
>     resolutionStrategy.cacheDynamicVersionsFor 0, 'seconds'
> }
> ```
>
> ref: https://github.com/spring-gradle-plugins/dependency-management-plugin/issues/74#issuecomment-182484694
>
> Sometimes it's right, sometimes ... no more warrants.

### Codes

> **Tips**
>
> 1. I am hands down `AlertDialog`.
> 2. Any codes about `ChooserDialog`, such as the following demo codes, should be only put into UI thread.

FileChooser android library give a simple file/folder chooser in single call (Fluent):

#### Choose a Folder

```java
    new ChooserDialog(MainActivity.this)
            .withFilter(true, false)
        	.withStartFile(startingDir)
        	// to handle the result(s)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(MainActivity.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();
```

#### Choose a File

```java
    new ChooserDialog(MainActivity.this)
            .withStartFile(path)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(MainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                }
            })
        	// to handle the back key pressed or clicked outside the dialog:
        	.withOnCancelListener(new DialogInterface.OnCancelListener() {
    			public void onCancel(DialogInterface dialog) {
			        Log.d("CANCEL", "CANCEL");
			        dialog.cancel(); // MUST have
    			}
			})
            .build()
            .show();

```

#### Wild-match

```java
    new ChooserDialog(MainActivity.this)
            .withFilter(false, false, "jpg", "jpeg", "png")
            .withStartFile(path)
            .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(MainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();

```

#### Regex filter

```java
    new ChooserDialog(MainActivity.this)
            .withFilterRegex(false, false, ".*\\.(jpe?g|png)")
            .withStartFile(path)
            .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(NewMainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();

```

#### Date Format String

Since 1.1.3, new builder options `withDateFormat(String)` added.

```java
    new ChooserDialog(MainActivity.this)
            .withFilter(true, false)
            .withStartFile(startingDir)
            .withDateFormat("HH:mm")    // see also SimpleDateFormat format specifiers
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(MainActivity.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();
```

#### Modify Icon or View Layout of `AlertDialog`:

Since 1.1.6, 2 new options are available:

```java
    new ChooserDialog(MainActivity.this)
            .withFilter(true, false)
            .withStartFile(startingDir)
            .withIcon(R.drawable.ic_file_chooser)
            .withLayoutView(R.layout.alert_file_chooser) // (API > 20)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(MainActivity.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();
```

#### Customizable NegativeButton

1.1.7 or Higher, try `withNegativeButton()` and/or `withNegativeButtonListener()`

---

#### withOnBackPressedListener

`BackPressedListener` will be called every time back key is pressed, and current directory is not the root of Primary/SdCard storage.
`LastBackPressedListener` will be called if back key is pressed, and current directory is the root of Primary/SdCard storage.

```java
.withOnBackPressedListener(dialog -> chooserDialog.goBack())
.withOnLastBackPressedListener(dialog -> dialog.cancel())
```


#### onCancelListener

`OnCancelListener` will be called when touching outside the dialog (`cancelOnTouchOutside` must be set true), and when pressing back key.
If `BackPressedListener` is overridden, it wont be called if `dialog.dismiss` is used instead of `dialog.cancel`.
`OnCancelListener` will **NOT** be called when pressing the negative button. use `withNegativeButtonListener` for that.

```java
.withOnCancelListener(new DialogInterface.OnCancelListener() {
    public void onCancel(DialogInterface dialog) {
        Log.d("CANCEL", "CANCEL");
    }
})

---

#### New calling chain

1.1.7+, new constructor `ChooserDialog(context)` can simplify the chain invoking. Also `build()` is no longer obligatory to be called:

​```java
    new ChooserDialog(MainActivity.this)
            .withFilter(true, false)
            .withStartFile(startingDir)
            ...
			.show();
```

And, old style is still available. No need to modify your existing codes.

#### `withRowLayoutView(resId)`

1.1.8+. Now you can customize each row.

since 1.1.17, `DirAdatper.GetViewListener#getView` allows you do the same thing and more, and `withRowLayoutView` will be deprecated. See also: `withAdapterSetter(setter)`

#### `withFileIcons`

1.1.9+. `withFileIcons(resolveMime, fileIcon, folderIcon)` and
`withFileIconsRes(resolveMime, fileIconResId, folderIconResId)` allow
user-defined file/folder icon.

`resolveMime`: true means that `DirAdapter` will try get icon from the associated app with the file's mime type.

```java
    final Context ctx = MainActivity.this;
    new ChooserDialog(ctx)
            .withStartFile(_path)
            .withResources(R.string.title_choose_any_file, R.string.title_choose, R.string.dialog_cancel)
            .withFileIconsRes(false, R.mipmap.ic_my_file, R.mipmap.ic_my_folder)
            .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(ctx, "FILE: " + path, Toast.LENGTH_SHORT).show();
                }
            })
            .build()
            .show();
```

#### `withAdapterSetter(setter)`

1.1.9+. a `AdapterSetter` can be use to customize the `DirAdapter`.

```java
.withAdapterSetter(new ChooserDialog.AdapterSetter() {
    @Override
    public void apply(DirAdapter adapter) {
        adapter.setDefaultFileIcon(fileIcon);
        adapter.setDefaultFolderIcon(folderIcon);
        adapter.setResolveFileType(tryResolveFileTypeAndIcon);
		// since 1.1.17
		adapter.overrideGetView((file, isSelected, isFocused, convertView, parent, inflater) -> {
			ViewGroup view = (ViewGroup) inflater.inflate(R.layout.li_row, parent, false);
			...
			return view;
		}
    }
})
```

More information in source code of `DirAdapter`.

since 1.1.17, `DirAdapter.overrideGetView()` supports GetViewListener interface.

```java
    public interface GetView {
        /**
         * @param file        file that should me displayed
         * @param isSelected  whether file is selected when _enableMultiple is set to true
         * @param isFocused   whether this file is focused when using dpad controls
		 					  deprecated since 1.1.18! use fileListItemFocusedDrawable attribute instead
         * @param convertView see ArrayAdapter#getView(int, View, ViewGroup)
         * @param parent      see ArrayAdapter#getView(int, View, ViewGroup)
         * @param inflater    a layout inflater with the FileChooser theme wrapped context
         * @return your custom row item view
         */
        @NonNull
        View getView(@NonNull File file, boolean isSelected, boolean isFocused, View convertView,
            @NonNull ViewGroup parent, @NonNull LayoutInflater inflater);
    }
```



#### `withNavigateUpTo(CanNavigateUp)`

1.1.10+. `withNavigateUpTo`

You can disallow someone enter some special directories.

```java
.withNavigateUpTo(new ChooserDialog.CanNavigateUp() {
    @Override
    public boolean canUpTo(File dir) {
        return true;
    }
})
```

#### `withNavigateTo(CanNavigateTo)`

1.1.10+. `withNavigateTo`

With `withStartFile()`, you can limit the root folder.

```java
.withNavigateTo(new ChooserDialog.CanNavigateTo() {
    @Override
    public boolean canNavigate(File dir) {
        return true;
    }
})
```

#### `enableOptions(true)`

a tri-dot menu icon will be shown at bottom left corner. this icon button allows end user to create new folder on the fly or delete one.

further tunes:
- `withOptionResources(@StringRes int createDirRes, @StringRes int deleteRes, @StringRes int newFolderCancelRes, @StringRes int newFolderOkRes)`

- `withOptionStringResources(@Nullable String createDir, @Nullable String delete,
          @Nullable String newFolderCancel, @Nullable String newFolderOk)`

  *since v1.1.17*

- `withOptionIcons(@DrawableRes int optionsIconRes, @DrawableRes int createDirIconRes, @DrawableRes int deleteRes)`

- `withNewFolderFilter(NewFolderFilter filter)`

- `withOnBackPressedListener(OnBackPressedListener listener)`

- `withOnLastBackPressedListener(OnBackPressedListener listener)`

see the sample codes in demo app.

**NOTE**:

1. extra `MANAGE_EXTERNAL_STORAGE` permission should be declared in your `AndroidManifest.xml`.
2. remember, that `MANAGE_EXTERNAL_STORAGE` needs [valid reasons](https://support.google.com/googleplay/android-developer/answer/10467955) to be able to use it
3. you can check with `Environment.isExternalStorageManager()`, whether the user gave you the permission or not
4. you can open the relevant setting for the user by something like the following in your `Fragment`:
```java
ActivityResultLauncher<Intent> permissionRequestedResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> checkPermissionGranted());
binding.openPermissonButton.setOnClickListener(v ->
		permissionRequestedResultLauncher.launch(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
);
```

#### `disableTitle(true)`

as named as working.


#### psuedo `.. SDCard Storage` and `.. Primary Storage`

since v1.11, external storage will be detected automatically. That means user can switch between internal and external storage by clicking on psuedo folder names. 


#### `titleFollowsDir(true)`

since the latest patch of v1.14, it allows the chooser dialog title updated by changing directory.


#### `displayPath(true)`, `customizePathView(callback)`

since the latest patch of v1.15, it allows a path string displayed below the title area.

since v1.16, its default value is true.

[Screen Snapshot](https://user-images.githubusercontent.com/27736965/53578348-f1d35880-3b90-11e9-9ef4-7ed0276ca603.gif)

As a useful complement, `customizePathView(callback)` allows tuning the path TextView. For example:

```java
.customizePathView((pathView) -> {
    pathView.setGravity(Gravity.RIGHT);
})
```

simce 1.1.17, this can also be done through a custom theme:

```xml
<style name="FileChooserStyle">
	...
	<item name="fileChooserPathViewStyle">@style/FileChooserPathViewStyle</item>
</style>

<style name="FileChooserPathViewStyle">
	<item name="android:background">#ffffffff</item>
	<item name="android:textColor">#40000000</item>
	<item name="android:textSize">12sp</item>
	<item name="fileChooserPathViewElevation">2</item>
	<item name="fileChooserPathViewDisplayRoot">true</item>
</style>
```

#### withResources, withStringResources

you can customize the text of buttons:

```java
            .withResources(R.string.title_choose_any_file, R.string.title_choose, R.string.dialog_cancel)
            .withStringResources("Title", "OK", "Cancel")
```





---

### Under Kotlin

```kotlin
class MyFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		val root = inflater.inflate(R.layout.fragment_book, container, false)
		root.upload_button.setOnClickListener { _: View ->
			ChooserDialog().with(activity)
				.withStartFile(Environment.getExternalStorageDirectory().absolutePath)
				// .withStartFile(Environment.getExternalStorageState()+"/")
				.withFilterRegex(false, false, ".*\\.(jpe?g|png)")
				.titleFollowsDir(true)
				.displayPath(true)
				.customizePathView { pathView -> pathView.setGravity(Gravity.RIGHT) }
				.withChosenListener { path, pathFile -> activity!!.toast("FILE: $path / $pathFile") }
				.build()
				.show()
		}

		return root
	}
}
```

And:

```kotlin
        ChooserDialog(context)
                .withFilterRegex(false, true, ".*\\.(jpe?g|png)")
                .withStartFile(startPath)
                .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener { path, pathFile ->
                    Toast.makeText(context, "FILE: $path; PATHFILE: $pathFile", Toast.LENGTH_SHORT).show()

                    //_path = path
                    //_tv.setText(_path)
                    ////_iv.setImageURI(Uri.fromFile(pathFile));
                    //_iv.setImageBitmap(ImageUtil.decodeFile(pathFile))
                }
                .withNavigateUpTo { true }
                .withNavigateTo { true }
                .build()
                .show()
```



## What's Different?

- ...
- ...
- ...

For more information please refer to the [upstream repo](https://github.com/hedzr/android-file-chooser).\


## Acknowledges

many peoples report or contribute to improve me, but only a few of them be put here — it's hard to list all.

- logo and banner by: [**iqbalhood**](https://github.com/iqbalhood)
- codes and reports: [**bostrot**](https://github.com/bostrot), [**SeppPenner**](https://github.com/SeppPenner), [**lucian-cm**](https://github.com/lucian-cm), [**ghost**](https://github.com/ghost), [**UmeshBaldaniya46**](https://github.com/UmeshBaldaniya46), ...
- especially, the Supporter/Collabotor: [Guiorgy](https://github.com/Guiorgy) and his [android-smbfile-chooser](https://github.com/Guiorgy/android-smbfile-chooser)



## License

Standard Apache 2.0

Copyright 2015-2019 Hedzr Yeh.
Modified 2022 1tchy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
