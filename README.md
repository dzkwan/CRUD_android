# Aplikasi CRUD Android data karyawan sederhana
aplikasi dibuat dengan menggunakan android java versi 17.\
silahkan diganti versinya sesuai dengan versi java yang dimiliki
```
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
- detail karyawan by click the itemlist
- update dan delete karyawan, swipe ke kiri pada itemlist
- delete multiple itemlist at once
- pull down refreshing list
- double back untuk keluar aplikasi
- Auto login ketika sudah pernah masuk, kecuali sudah di logout