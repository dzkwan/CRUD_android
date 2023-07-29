# Aplikasi CRUD Android data karyawan sederhana
aplikasi dibuat dengan menggunakan android java versi 17.\
silahkan diganti versinya sesuai dengan versi java yang dimiliki
```java
compileOptions {
    sourceCompatibility JavaVersion.VERSION_17
    targetCompatibility JavaVersion.VERSION_17
}
```
tools yang digunakan:
- swipe refresh layout
- swipe reveal layout
```groovy
dependencies {
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01'
    implementation 'com.apachat:swipereveallayout-android:1.1.2'
}
```

Fitur pada aplikasi:

- Register, Login, dan Logout
- Update akun
- Add data karyawan
- detail karyawan, klik pada nama listitem
- update dan delete karyawan, swipe ke kiri pada listitem
- delete multiple listitem at once
- Auto login ketika sudah pernah masuk, kecuali sudah di logout