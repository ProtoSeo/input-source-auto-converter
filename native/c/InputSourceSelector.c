//
//  InputSourceSelector.c
//  native
//
//  Created by 서승훈 on 1/12/24.
//

#include "InputSourceSelector.h"

int main(int argc, char* argv[]) {
    if (argc != 2) {
        fprintf(stderr,"Input source not found\n");
        return 0;
    }
    char* sourceId = argv[1];
    selectInputSource(sourceId);
}

void selectInputSource(char* sourceId) {
    TISInputSourceRef inputSourceRef = getInputSourceRef(sourceId);
    
    if (inputSourceRef) {
        CFBooleanRef enabled = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceIsEnabled);
        if (enabled == kCFBooleanFalse) {
            TISEnableInputSource(inputSourceRef);
        }
        TISSelectInputSource(inputSourceRef);
        CFRelease(inputSourceRef);
    }
}

TISInputSourceRef getInputSourceRef(char *sourceId) {
    CFStringRef name = CFStringCreateWithCString(kCFAllocatorDefault, sourceId, kCFStringEncodingUTF8);

    CFStringRef keys[] = { kTISPropertyInputSourceID };
    CFStringRef values[] = { name };
    CFDictionaryRef dict = CFDictionaryCreate(kCFAllocatorDefault, (const void **)keys, (const void **)values, 1, NULL, NULL);
    
    CFArrayRef array = TISCreateInputSourceList(dict, false);
    
    if (!array) {
        fprintf(stderr,"Specified input source \"%s\" not found\n", sourceId);
        return NULL;
    }

    TISInputSourceRef inputSourceRef = (TISInputSourceRef) CFArrayGetValueAtIndex(array, 0);
    CFRetain(inputSourceRef);

    return inputSourceRef;
}
