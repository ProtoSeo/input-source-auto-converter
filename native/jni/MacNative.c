#include "com_protoseo_inputsourceautoconverter_converter_MacNative.h"
#include <Carbon/Carbon.h>
#include <string.h>
#include <stdlib.h>
#include <jni.h>

void appendToString(char **dest, const char *src) {
    if (*dest == NULL) {
        *dest = (char *)malloc(strlen(src) + 1);
        strcpy(*dest, src);
    } else {
        char *temp = (char *)realloc(*dest, strlen(*dest) + strlen(src) + 1);
        *dest = temp;
        strcat(*dest, src);
    }
}

JNIEXPORT jstring JNICALL Java_com_protoseo_inputsourceautoconverter_converter_MacNative_findSelectableInputSources(JNIEnv *env, jobject obj) {
    CFArrayRef inputSources = TISCreateInputSourceList(NULL, false);
    CFIndex inputSourcesSize = CFArrayGetCount(inputSources);
    if (inputSourcesSize == 0) {
        jstring result = (*env)->NewStringUTF(env, "");
        return result;
    }

    char *inputSourcesString = NULL;
    for (CFIndex i = 0; i < inputSourcesSize; i++) {
        TISInputSourceRef inputSourceRef = (TISInputSourceRef) CFArrayGetValueAtIndex(inputSources, i);
        
        CFStringRef sourceId = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceID);
        CFStringRef name = TISGetInputSourceProperty(inputSourceRef, kTISPropertyLocalizedName);
        CFStringRef category = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceCategory);
        CFArrayRef languages = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceLanguages);
        CFBooleanRef selectable = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceIsSelectCapable);
        
        if (CFBooleanGetValue(selectable) && !CFStringCompare(category, kTISCategoryKeyboardInputSource, kCFCompareCaseInsensitive)) {
            int sourceIdLen = (int)CFStringGetLength(sourceId) * 4 + 1;
            char sourceIdStr[sourceIdLen];
            CFStringGetCString(sourceId, sourceIdStr, sourceIdLen, kCFStringEncodingUTF8);

            int nameLen = (int)CFStringGetLength(name) * 4 + 1;
            char nameStr[nameLen];
            CFStringGetCString(name, nameStr, nameLen, kCFStringEncodingUTF8);

            CFStringRef mainLang = (CFStringRef) CFArrayGetValueAtIndex(languages, 0);
            int mainLangLen = (int)CFStringGetLength(mainLang) * 4 + 1;
            char mainLangStr[mainLangLen];
            CFStringGetCString(mainLang, mainLangStr, mainLangLen, kCFStringEncodingUTF8);
    
            char buffer[sourceIdLen + nameLen + mainLangLen + 10];
            snprintf(buffer, sizeof(buffer), "%s:%s:%s\n", sourceIdStr, nameStr, mainLangStr);

            appendToString(&inputSourcesString, buffer);
        }
    }

    jstring result = (*env)->NewStringUTF(env, inputSourcesString);
    free(inputSourcesString);
    return result;
}

JNIEXPORT jboolean JNICALL Java_com_protoseo_inputsourceautoconverter_converter_MacNative_convert(JNIEnv *env, jobject obj, jstring inputSource) {
    const char* sourceId = (*env)->GetStringUTFChars(env, inputSource, NULL);

    CFStringRef name = CFStringCreateWithCString(kCFAllocatorDefault, sourceId, kCFStringEncodingUTF8);

    CFStringRef keys[] = { kTISPropertyInputSourceID };
    CFStringRef values[] = { name };
    CFDictionaryRef dict = CFDictionaryCreate(kCFAllocatorDefault, (const void **)keys, (const void **)values, 1, NULL, NULL);
    
    CFArrayRef array = TISCreateInputSourceList(dict, false);
    (*env)->ReleaseStringUTFChars(env, inputSource, sourceId);
    
    if (!array) {
        fprintf(stderr,"Specified input source \"%s\" not found\n", sourceId);
        return JNI_FALSE;
    }

    TISInputSourceRef inputSourceRef = (TISInputSourceRef) CFArrayGetValueAtIndex(array, 0);
    CFRetain(inputSourceRef);

    if (inputSourceRef) {
        CFBooleanRef enabled = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceIsEnabled);
        if (enabled == kCFBooleanFalse) {
            TISEnableInputSource(inputSourceRef);
        }
        TISSelectInputSource(inputSourceRef);
        CFRelease(inputSourceRef);
        return JNI_TRUE;
    }
    return JNI_FALSE;
}
