
#include <LogLib.h>
#include <android/log.h>
#define NULL ((void *) 0)

static void ThrowExceptionByClassName(JNIEnv *env, const char *name, const char *message) {
	jclass clazz = (*env)->FindClass(env, name);
	if (clazz != NULL) {
		(*env)->ThrowNew(env, clazz, message);
		(*env)->DeleteLocalRef(env, clazz);
	}
}

JNIEXPORT void JNICALL Java_com_marakana_android_lognative_LogLib_logN
(JNIEnv *env, jclass klass, jint level, jstring tag, jstring msg)
{
	if ((ANDROID_LOG_VERBOSE > level) || (ANDROID_LOG_ERROR < level)) {
		ThrowExceptionByClassName(env, "java/lang/IllegalArgumentException", "Invalid priority");
	}
	else if (NULL == tag) {
		ThrowExceptionByClassName(env, "java/lang/NullPointerException", "null tag");
	}
	else if (0 >= (*env)->GetStringLength(env, tag)) {
		ThrowExceptionByClassName(env, "java/lang/IllegalArgumentException", "empty tag");
	}
	else if (NULL == msg) {
		ThrowExceptionByClassName(env, "java/lang/NullPointerException", "null message");
	}
	else if (0 >= (*env)->GetStringLength(env, msg)) {
		ThrowExceptionByClassName(env, "java/lang/IllegalArgumentException", "empty message");
	}
	else {
		const char *cTag = (*env)->GetStringUTFChars(env, tag, NULL);
		if (cTag) {
			const char *cMsg = (*env)->GetStringUTFChars(env, msg, NULL);
			if (cMsg) {
				__android_log_write(level, cTag, cMsg);
				(*env)->ReleaseStringUTFChars(env, msg, cMsg);
			}
			(*env)->ReleaseStringUTFChars(env, tag, cTag);
		}
	}
}


