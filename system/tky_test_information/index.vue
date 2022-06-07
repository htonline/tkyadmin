<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">报验单id</label>
        <el-input v-model="query.id" clearable placeholder="报验单id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">项目id</label>
        <el-input v-model="query.xmid" clearable placeholder="项目id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">项目名称</label>
        <el-input v-model="query.xmname" clearable placeholder="项目名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">标段id</label>
        <el-input v-model="query.bdid" clearable placeholder="标段id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">标段名称</label>
        <el-input v-model="query.bdname" clearable placeholder="标段名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">
构筑物id</label>
        <el-input v-model="query.gzwid" clearable placeholder="
构筑物id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">构筑物名称</label>
        <el-input v-model="query.gzwname" clearable placeholder="构筑物名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">工点id</label>
        <el-input v-model="query.gdid" clearable placeholder="工点id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">工点名称</label>
        <el-input v-model="query.gdname" clearable placeholder="工点名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">施工单位id</label>
        <el-input v-model="query.sgdwid" clearable placeholder="施工单位id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">施工单位名称</label>
        <el-input v-model="query.sgdwname" clearable placeholder="施工单位名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">监理单位id</label>
        <el-input v-model="query.jldwid" clearable placeholder="监理单位id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">监理单位名称</label>
        <el-input v-model="query.jldwname" clearable placeholder="监理单位名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测单位id</label>
        <el-input v-model="query.jcdwid" clearable placeholder="检测单位id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测单位名称</label>
        <el-input v-model="query.jcdwname" clearable placeholder="检测单位名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测方法</label>
        <el-input v-model="query.jcff" clearable placeholder="检测方法" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">试验方法</label>
        <el-input v-model="query.syff" clearable placeholder="试验方法" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">联系人</label>
        <el-input v-model="query.lxr" clearable placeholder="联系人" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">联系电话</label>
        <el-input v-model="query.lxdh" clearable placeholder="联系电话" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">预约检测日期</label>
        <el-input v-model="query.byrq" clearable placeholder="预约检测日期" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">施工负责人</label>
        <el-input v-model="query.sgfzr" clearable placeholder="施工负责人" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测起始里程编码</label>
        <el-input v-model="query.jcqslcbm" clearable placeholder="检测起始里程编码" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测起始里程1</label>
        <el-input v-model="query.jcqslc1" clearable placeholder="检测起始里程1" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测起始里程编码</label>
        <el-input v-model="query.jcjslcbm" clearable placeholder="检测起始里程编码" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测结束里程1</label>
        <el-input v-model="query.jcjslc1" clearable placeholder="检测结束里程1" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测结束里程2</label>
        <el-input v-model="query.jcjslc2" clearable placeholder="检测结束里程2" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测长度</label>
        <el-input v-model="query.jccd" clearable placeholder="检测长度" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测部位</label>
        <el-input v-model="query.jcbw" clearable placeholder="检测部位" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测方式</label>
        <el-input v-model="query.jcfs" clearable placeholder="检测方式" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测类型</label>
        <el-input v-model="query.jclx" clearable placeholder="检测类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">围岩等级</label>
        <el-input v-model="query.wydj" clearable placeholder="围岩等级" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">浇筑日期(施工日期)</label>
        <el-input v-model="query.jzrq" clearable placeholder="浇筑日期(施工日期)" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">示意图</label>
        <el-input v-model="query.syt" clearable placeholder="示意图" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">施工单位发布状态</label>
        <el-input v-model="query.sgdwfbzt" clearable placeholder="施工单位发布状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">流程状态</label>
        <el-input v-model="query.lczt" clearable placeholder="流程状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">提交备注（施工单位）</label>
        <el-input v-model="query.tjbz" clearable placeholder="提交备注（施工单位）" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">报检单编号</label>
        <el-input v-model="query.bydbh" clearable placeholder="报检单编号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测进度状态</label>
        <el-input v-model="query.jcjdzt" clearable placeholder="检测进度状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测结论备注</label>
        <el-input v-model="query.jcjlbz" clearable placeholder="检测结论备注" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">发布状态</label>
        <el-input v-model="query.zt" clearable placeholder="发布状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">buildType</label>
        <el-input v-model="query.buildtype" clearable placeholder="buildType" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="报验单id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目id">
            <el-input v-model="form.xmid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="form.xmname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="标段id">
            <el-input v-model="form.bdid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="标段名称">
            <el-input v-model="form.bdname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="
构筑物id">
            <el-input v-model="form.gzwid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="构筑物名称">
            <el-input v-model="form.gzwname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="工点id">
            <el-input v-model="form.gdid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="工点名称">
            <el-input v-model="form.gdname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="施工单位id">
            <el-input v-model="form.sgdwid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="施工单位名称">
            <el-input v-model="form.sgdwname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="监理单位id">
            <el-input v-model="form.jldwid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="监理单位名称">
            <el-input v-model="form.jldwname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测单位id">
            <el-input v-model="form.jcdwid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测单位名称">
            <el-input v-model="form.jcdwname" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测方法">
            <el-input v-model="form.jcff" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="试验方法">
            <el-input v-model="form.syff" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.lxr" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.lxdh" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="预约检测日期">
            <el-input v-model="form.byrq" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="施工负责人">
            <el-input v-model="form.sgfzr" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测起始里程编码">
            <el-input v-model="form.jcqslcbm" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测起始里程1">
            <el-input v-model="form.jcqslc1" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测起始里程编码">
            <el-input v-model="form.jcjslcbm" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测结束里程1">
            <el-input v-model="form.jcjslc1" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测结束里程2">
            <el-input v-model="form.jcjslc2" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测长度">
            <el-input v-model="form.jccd" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测部位">
            <el-input v-model="form.jcbw" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测方式">
            <el-input v-model="form.jcfs" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测类型">
            <el-input v-model="form.jclx" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="围岩等级">
            <el-input v-model="form.wydj" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="浇筑日期(施工日期)">
            <el-input v-model="form.jzrq" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="示意图">
            <el-input v-model="form.syt" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="施工单位发布状态">
            <el-input v-model="form.sgdwfbzt" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="流程状态">
            <el-input v-model="form.lczt" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="提交备注（施工单位）">
            <el-input v-model="form.tjbz" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="报检单编号">
            <el-input v-model="form.bydbh" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测进度状态">
            <el-input v-model="form.jcjdzt" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测结论备注">
            <el-input v-model="form.jcjlbz" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发布状态">
            <el-input v-model="form.zt" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="buildType">
            <el-input v-model="form.buildtype" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="报验单id" />
        <el-table-column prop="xmid" label="项目id" />
        <el-table-column prop="xmname" label="项目名称" />
        <el-table-column prop="bdid" label="标段id" />
        <el-table-column prop="bdname" label="标段名称" />
        <el-table-column prop="gzwid" label="
构筑物id" />
        <el-table-column prop="gzwname" label="构筑物名称" />
        <el-table-column prop="gdid" label="工点id" />
        <el-table-column prop="gdname" label="工点名称" />
        <el-table-column prop="sgdwid" label="施工单位id" />
        <el-table-column prop="sgdwname" label="施工单位名称" />
        <el-table-column prop="jldwid" label="监理单位id" />
        <el-table-column prop="jldwname" label="监理单位名称" />
        <el-table-column prop="jcdwid" label="检测单位id" />
        <el-table-column prop="jcdwname" label="检测单位名称" />
        <el-table-column prop="jcff" label="检测方法" />
        <el-table-column prop="syff" label="试验方法" />
        <el-table-column prop="lxr" label="联系人" />
        <el-table-column prop="lxdh" label="联系电话" />
        <el-table-column prop="byrq" label="预约检测日期" />
        <el-table-column prop="sgfzr" label="施工负责人" />
        <el-table-column prop="jcqslcbm" label="检测起始里程编码" />
        <el-table-column prop="jcqslc1" label="检测起始里程1" />
        <el-table-column prop="jcjslcbm" label="检测起始里程编码" />
        <el-table-column prop="jcjslc1" label="检测结束里程1" />
        <el-table-column prop="jcjslc2" label="检测结束里程2" />
        <el-table-column prop="jccd" label="检测长度" />
        <el-table-column prop="jcbw" label="检测部位" />
        <el-table-column prop="jcfs" label="检测方式" />
        <el-table-column prop="jclx" label="检测类型" />
        <el-table-column prop="wydj" label="围岩等级" />
        <el-table-column prop="jzrq" label="浇筑日期(施工日期)" />
        <el-table-column prop="syt" label="示意图" />
        <el-table-column prop="sgdwfbzt" label="施工单位发布状态" />
        <el-table-column prop="lczt" label="流程状态" />
        <el-table-column prop="tjbz" label="提交备注（施工单位）" />
        <el-table-column prop="bydbh" label="报检单编号" />
        <el-table-column prop="jcjdzt" label="检测进度状态" />
        <el-table-column prop="jcjlbz" label="检测结论备注" />
        <el-table-column prop="zt" label="发布状态" />
        <el-table-column prop="buildtype" label="buildType" />
        <el-table-column v-if="checkPer(['admin','tkyTestInformation:edit','tkyTestInformation:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudTkyTestInformation from '@/api/tkyTestInformation'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, xmid: null, xmname: null, bdid: null, bdname: null, gzwid: null, gzwname: null, gdid: null, gdname: null, gcid: null, gcname: null, sgdwid: null, sgdwname: null, jldwid: null, jldwname: null, jcdwid: null, jcdwname: null, jcff: null, syff: null, lxr: null, lxdh: null, byrq: null, jcsb: null, jxrq: null, jxzq: null, sgfzr: null, jcqslcbm: null, jcqslc1: null, jcqslc2: null, jcjslcbm: null, jcjslc1: null, jcjslc2: null, jccd: null, cqsl: null, jcbw: null, jcfs: null, jclx: null, zxbw: null, sjhd: null, tchd: null, sjqd: null, mgbh: null, mgzj: null, mglx: null, mgsl: null, wydj: null, sjmgcd: null, sjmgkbl: null, jzrq: null, shejmgcd: null, shejmgkbl: null, lqqdbg: null, syt: null, zjbg: null, zjbgjcyj: null, zjbgjj: null, zjbgjy: null, jcsjfj: null, jcsjfjscsj: null, sgdwfbzt: null, lczt: null, tjr: null, tjbz: null, tjsj: null, spyj: null, spr: null, spbz: null, spsj: null, bjfw: null, bjsl: null, bydbh: null, jcfxsj: null, jcfxr: null, jcyj: null, jcr: null, jcbz: null, jcsj: null, jcjdzt: null, jcjlbz: null, jcjl: null, jcjlfj: null, fhjcjg: null, fhspyj: null, fhspr: null, fhspbz: null, fhspsj: null, zt: null, fbsj: null, fbr: null, testType: null, holeMethod: null, concreteStrength: null, desighOrg: null, desighOrgName: null, inspectionOrg: null, inspectionOrgName: null, buildtype: null, createdate: null, createdatestr: null, zjbgjc: null }
export default {
  name: 'TkyTestInformation',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/tky_test_information', url: 'api/tkyTestInformation', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTkyTestInformation }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tkyTestInformation:add'],
        edit: ['admin', 'tkyTestInformation:edit'],
        del: ['admin', 'tkyTestInformation:del']
      },
      rules: {
        id: [
          { required: true, message: '报验单id不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'id', display_name: '报验单id' },
        { key: 'xmid', display_name: '项目id' },
        { key: 'xmname', display_name: '项目名称' },
        { key: 'bdid', display_name: '标段id' },
        { key: 'bdname', display_name: '标段名称' },
        { key: 'gzwid', display_name: '
构筑物id' },
        { key: 'gzwname', display_name: '构筑物名称' },
        { key: 'gdid', display_name: '工点id' },
        { key: 'gdname', display_name: '工点名称' },
        { key: 'sgdwid', display_name: '施工单位id' },
        { key: 'sgdwname', display_name: '施工单位名称' },
        { key: 'jldwid', display_name: '监理单位id' },
        { key: 'jldwname', display_name: '监理单位名称' },
        { key: 'jcdwid', display_name: '检测单位id' },
        { key: 'jcdwname', display_name: '检测单位名称' },
        { key: 'jcff', display_name: '检测方法' },
        { key: 'syff', display_name: '试验方法' },
        { key: 'lxr', display_name: '联系人' },
        { key: 'lxdh', display_name: '联系电话' },
        { key: 'byrq', display_name: '预约检测日期' },
        { key: 'sgfzr', display_name: '施工负责人' },
        { key: 'jcqslcbm', display_name: '检测起始里程编码' },
        { key: 'jcqslc1', display_name: '检测起始里程1' },
        { key: 'jcjslcbm', display_name: '检测起始里程编码' },
        { key: 'jcjslc1', display_name: '检测结束里程1' },
        { key: 'jcjslc2', display_name: '检测结束里程2' },
        { key: 'jccd', display_name: '检测长度' },
        { key: 'jcbw', display_name: '检测部位' },
        { key: 'jcfs', display_name: '检测方式' },
        { key: 'jclx', display_name: '检测类型' },
        { key: 'wydj', display_name: '围岩等级' },
        { key: 'jzrq', display_name: '浇筑日期(施工日期)' },
        { key: 'syt', display_name: '示意图' },
        { key: 'sgdwfbzt', display_name: '施工单位发布状态' },
        { key: 'lczt', display_name: '流程状态' },
        { key: 'tjbz', display_name: '提交备注（施工单位）' },
        { key: 'bydbh', display_name: '报检单编号' },
        { key: 'jcjdzt', display_name: '检测进度状态' },
        { key: 'jcjlbz', display_name: '检测结论备注' },
        { key: 'zt', display_name: '发布状态' },
        { key: 'buildtype', display_name: 'buildType' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
