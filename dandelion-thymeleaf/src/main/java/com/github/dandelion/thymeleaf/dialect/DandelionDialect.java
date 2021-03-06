/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2015 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.thymeleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.github.dandelion.thymeleaf.processor.AssetAttrProcessor;
import com.github.dandelion.thymeleaf.processor.BundleAttrProcessor;

/**
 * <p>
 * Thymeleaf Dialect for Dandelion.
 * 
 * @author Romain Lespinasse
 * @author Thibault Duchateau
 */
public class DandelionDialect extends AbstractDialect {

   public static final String DIALECT_PREFIX = "ddl";
   public static final String LAYOUT_NAMESPACE = "http://www.thymeleaf.org/dandelion";
   public static final int HIGHEST_PRECEDENCE = 3500;

   public String getPrefix() {
      return DIALECT_PREFIX;
   }

   public boolean isLenient() {
      return false;
   }

   @Override
   public Set<IProcessor> getProcessors() {
      final Set<IProcessor> processors = new HashSet<IProcessor>();

      for (AssetAttributeNames attr : AssetAttributeNames.values()) {
         processors.add(new AssetAttrProcessor(attr.getAttribute()));
      }

      for (BundleAttributeNames attr : BundleAttributeNames.values()) {
         processors.add(new BundleAttrProcessor(attr.getAttribute()));
      }

      return processors;
   }
}
