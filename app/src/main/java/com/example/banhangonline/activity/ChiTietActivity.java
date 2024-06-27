package com.example.banhangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.banhangonline.R;
import com.example.banhangonline.model.GioHang;
import com.example.banhangonline.model.SanPhamMoi;
import com.example.banhangonline.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
        boolean flag = false;
        // Loại bỏ các ký tự không phải số khỏi chuỗi giá sản phẩm
        long giaSanPham = Long.parseLong(sanPhamMoi.getGiasp().replaceAll("[^\\d]", ""));

        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                Utils.manggiohang.get(i).setSoluong(Utils.manggiohang.get(i).getSoluong() + soluong);
                Utils.manggiohang.get(i).setGiasp(giaSanPham * Utils.manggiohang.get(i).getSoluong());
                flag = true;
                break;
            }
        }
        if (!flag) {
            long gia = giaSanPham * soluong;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            gioHang.setTensp(sanPhamMoi.getTensp());
            Utils.manggiohang.add(gioHang); // add vào mảng
        }
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }


    private long cleanAndParseLong(String giasp) {
        // Loại bỏ các ký tự không phải số (trừ dấu thập phân nếu cần)
        String cleanedString = giasp.replaceAll("[^\\d]", "");
        // Chuyển đổi chuỗi đã làm sạch sang số
        return Long.parseLong(cleanedString);
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        giasp.setText("Giá:" +sanPhamMoi.getGiasp());
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem+ Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}