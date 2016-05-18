/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.util;

/**
 * A copy of C++ implementation
 * @author WeAthFolD
 */
public class Pair<U, V> {
    public U first;
    public V second;
    
    public Pair(U k, V v) {
        first = k;
        second = v;
    }
    
    @Override
    public String toString() {
        return first + "," + second;
    }
    
}
