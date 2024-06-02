# PoliSee
### Deskripsi Aplikasi
Aplikasi PoliSee merupakan sebuah aplikasi yang dibuat untuk memperlihatkan daftar pejabat politik dari berbagai negara yang berasal dari API yang digunakan. Aplikasi ini akan membantu pengguna untuk menemukan informasi mengenai politikus yang mereka sukai berdasarkan profile dari politikus tersebut.

### Fitur-fitur Aplikasi
1. Menampilkan daftar politikus: pada fitur ini kita akan diperlihatkan daftar politikus ketika baru masuk ke aplikasi.
2. Detail politikus: menampilkan informasi lengkap mengenai politikus terkait termasuk posisi, partai, dan biografi.
3. Pencarian politikus: di pencarian politikus kita dapat mencari berdasarkan nama politikus.
4. Favorite politikus: dimana kita dapat menambahkan politisi yang disukai kedalam favorite yang akan tersimpan di navigation favorite.
5. Daftar favorite politikus: daftar politikus mencakup semua politikus yang telah ditambahkan ke dalam favorite.
6. Penyimpanan data favorite: fitur penyimpanan data favorite menggunakan database SQLite untuk menyimpan data favorite secara lokal.
7. Mengarahkan ke website: pada aplikasi ini terdapat fitur mengarahkan ke website yang terdapat pada navigation about.

### Cara Penggunaan
1. Ketika di awal aplikasi dibuka maka akan menampilkan main activity yang isinya adalah sebuah judul aplikasi, gambar, beserta button yang mengarah ke halaman fragment home.
2. Ketika button "Let's Go" di klik maka akan memindahkan tampilan halaman ke fragment home.
3. Di dalam fragment home, terdapat daftar politisi yang akan di tampilkan yang mencakup profile, nama, posisi, negara asal, dan biografi.
4. Lalu, ketika salah satu daftar politisi di klik maka akan mengarahkan kita ke halaman detail.
5. Pada halaman detail, kita akan di tampilkan semua informasi mengenai politisi yang mencakup data yang berasal dari APi.
6. Pada halaman detail juga terdapat favorite yang bisa di klik untuk memasukkan politisi ke daftar favorite dan juga bisa di unfavorite dari daftar favorite di halaman detail.
7. Pada fragment search, terdapat searchview yang digunakan untuk mencari politisi berdasarkan namanya dan akan di tampilkan daftar politisinya.
8. Ketika daftar politisi yang dicari telah muncul, kita bisa mengklik dan akan di arahkan ke halaman detail politisi.
9. Pada fragment favorite, kita bisa melihat daftar politisi yang telah di tambahkan kedalam favorite, dan juga bisa di unfavorite dari fragment favorite dengan mengklik icon favoritenya.
10. Pada fragment about, terdapat button yang bisa mengarahkan kita ke halaman website yang dimana halaman websitenya itu tentang pengertian politikus atau jabatan politik.
