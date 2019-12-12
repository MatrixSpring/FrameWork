package com.dawn.libframe.sqlcipher;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.lang.ref.WeakReference;

public class DaoSupportFactory {
    // 持有外部数据库的引用
    private SQLiteDatabase mSQLiteDatabase;
    private static final String sDbName = "DemoDb";
    private static WeakReference<Context> sWeakReference;

    public static class DaoSupportInstance{
        public static DaoSupportFactory daoSupportFactory=new DaoSupportFactory();
    }

    public static DaoSupportFactory getInstance(Context context){
        sWeakReference = new WeakReference<>(context);
        return DaoSupportInstance.daoSupportFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport();
        daoSupport.init(mSQLiteDatabase, clazz);
        return daoSupport;
    }

    private DaoSupportFactory() {
        // 把数据库放到内存卡里面  判断是否有存储卡 6.0要动态申请权限
        File dbRoot = new File(sWeakReference.get().getFilesDir().getAbsolutePath() + File.separator + sDbName + File.separator + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "test.db");
        SQLiteDatabase.loadLibs(sWeakReference.get());
        // 打开或者创建一个数据库
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, "123465", null);
        // 默认数据库文件位置
        mSQLiteDatabase = SQLiteDatabase.create(null,"123465");
    }

}
