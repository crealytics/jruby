/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */

package org.jruby.truffle.core;

import com.oracle.truffle.api.object.DynamicObject;
import org.jcodings.specific.UTF8Encoding;
import org.jruby.truffle.language.RubyGuards;
import org.jruby.truffle.runtime.rope.CodeRange;
import org.jruby.truffle.runtime.rope.Rope;

public class StringGuards {

    public static boolean isSingleByteOptimizable(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return Layouts.STRING.getRope(string).isSingleByteOptimizable();
    }

    public static boolean is7Bit(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return StringOperations.getCodeRange(string) == CodeRange.CR_7BIT;
    }

    public static boolean isAsciiCompatible(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return Layouts.STRING.getRope(string).getEncoding().isAsciiCompatible();
    }

    public static boolean isSingleByteOptimizableOrAsciiOnly(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        // TODO (nirvdrum 08-Jun-15) Rubinius tracks whether a String is ASCII-only via a field in the String.
        return isSingleByteOptimizable(string);
    }

    public static boolean isSingleByte(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return Layouts.STRING.getRope(string).getEncoding().isSingleByte();
    }

    public static boolean isValidOr7BitEncoding(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        final Rope rope = StringOperations.rope(string);

        return (rope.getCodeRange() == CodeRange.CR_VALID) || (rope.getCodeRange() == CodeRange.CR_7BIT);
    }

    public static boolean isFixedWidthEncoding(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return Layouts.STRING.getRope(string).getEncoding().isFixedWidth();
    }

    public static boolean isValidUtf8(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return StringOperations.isCodeRangeValid(string) && Layouts.STRING.getRope(string).getEncoding() instanceof UTF8Encoding;
    }

    public static boolean isEmpty(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return Layouts.STRING.getRope(string).isEmpty();
    }

    public static boolean isBrokenCodeRange(DynamicObject string) {
        assert RubyGuards.isRubyString(string);
        return StringOperations.codeRange(string) == CodeRange.CR_BROKEN;
    }
}
