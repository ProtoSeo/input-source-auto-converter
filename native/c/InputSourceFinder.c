//
//  InputSourceFinder.c
//  native
//
//  Created by 서승훈 on 1/12/24.
//

#include "InputSourceFinder.h"

int main(void) {
    findSelectableInputSources();
}

void findSelectableInputSources(void) {
    CFArrayRef inputSources = TISCreateInputSourceList(NULL, false);
    CFIndex inputSourcesSize = CFArrayGetCount(inputSources);
    if (inputSourcesSize == 0) {
        return;
    }
    
    for (CFIndex i = 0; i < inputSourcesSize; i++) {
        TISInputSourceRef inputSourceRef = (TISInputSourceRef) CFArrayGetValueAtIndex(inputSources, i);
        
        CFStringRef sourceId = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceID);
        CFStringRef name = TISGetInputSourceProperty(inputSourceRef, kTISPropertyLocalizedName);
        CFStringRef category = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceCategory);
        CFArrayRef langs = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceLanguages);
        CFBooleanRef selectable = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceIsSelectCapable);
        
        if (CFBooleanGetValue(selectable) && !CFStringCompare(category, kTISCategoryKeyboardInputSource, kCFCompareCaseInsensitive)) {
            int sourceIdLength = (int)CFStringGetLength(sourceId) * 4 + 1;
            char sourceIdStr[sourceIdLength];
            CFStringGetCString(sourceId, sourceIdStr, sourceIdLength, kCFStringEncodingUTF8);

            int nameLength = (int)CFStringGetLength(name) * 4 + 1;
            char nameStr [nameLength];
            CFStringGetCString(name, nameStr, nameLength, kCFStringEncodingUTF8);

            CFStringRef mainLang = (CFStringRef) CFArrayGetValueAtIndex(langs, 0);
            int mainLangLen = (int)CFStringGetLength(mainLang) * 4 + 1;
            char mainLangStr[mainLangLen];
            CFStringGetCString(mainLang, mainLangStr, mainLangLen, kCFStringEncodingUTF8);

            printf("%s:%s:%s\n", sourceIdStr, nameStr, mainLangStr);
        }
    }
}
