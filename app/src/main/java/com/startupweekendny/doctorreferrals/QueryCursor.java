package com.startupweekendny.doctorreferrals;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by andres on 6/14/15.
 */
public class QueryCursor implements Cursor {

    List<ParseObject> queryList;
    int index = -1;

    public QueryCursor(List<ParseObject> myList) {
        queryList = myList;
    }

    @Override
    public int getCount() {
        return queryList.size();
    }

    @Override
    public int getPosition() {
        return index;
    }

    @Override
    public boolean move(int offset) {
        int aux = index + offset;
        if (aux <= getCount()) {
            index = aux;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean moveToPosition(int position) {
        if (position <= getCount()) {
            index = position;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean moveToFirst() {
        if (getCount() > 0) {
            index = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToLast() {
        if (getCount() > 0) {
            index = getCount() - 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToNext() {
        int aux = index + 1;
        if (aux <= getCount()) {
            index = aux;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToPrevious() {
        int aux = index - 1;
        if (aux >= -1) {
            index = aux;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFirst() {
        return index == 0;
    }

    @Override
    public boolean isLast() {
        return index == getCount()-1;
    }

    @Override
    public boolean isBeforeFirst() {
        return index < 0;
    }

    @Override
    public boolean isAfterLast() {
        return index >= getCount();
    }

    @Override
    public int getColumnIndex(String columnName) {
        if (columnName.equals("name")) {
            return 0;
        }
        if (columnName.equals("specialty")) {
            return 1;
        }
        if (columnName.equals("rating")) {
            return 2;
        }
        return -1;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        if (columnName.equals("name")) {
            return 0;
        }
        if (columnName.equals("specialty")) {
            return 1;
        }
        if (columnName.equals("rating")) {
            return 2;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "name";
            case 1: return "specialty";
            case 2: return "rating";
            default: return null;
        }
    }

    @Override
    public String[] getColumnNames() {
        return new String[] {"name", "specialty", "rating"};
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return null;
    }

    @Override
    public String getString(int columnIndex) {
        ParseObject object = queryList.get(index);
        switch (columnIndex) {
            case 0: return object.getString("FirstName") + " " + object.getString("LastName");
            case 1: return object.getString("Specialty");
            default: return null;
        }
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

    }

    @Override
    public short getShort(int columnIndex) {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) {
        ParseObject object = queryList.get(index);
        switch (columnIndex) {
            case 2: return object.getDouble("Rating");
            default: return 0;
        }
    }

    @Override
    public int getType(int columnIndex) {
        return 0;
    }

    @Override
    public boolean isNull(int columnIndex) {
        return false;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean requery() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {

    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {

    }

    @Override
    public Uri getNotificationUri() {
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override
    public Bundle getExtras() {
        return null;
    }

    @Override
    public Bundle respond(Bundle extras) {
        return null;
    }
}
